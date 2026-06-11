# BCB — Big Chat Brasil

Plataforma de chat para comunicação entre clientes, com suporte a planos pré-pago e pós-pago, fila de mensagens com prioridade e comunicação em tempo real via WebSocket.

Desenvolvido por **Victor** como solução para o desafio técnico fullstack BCB.

---

## Tecnologias

**Backend**
- Java 21 + Spring Boot
- Spring Security + JWT
- Spring Data JPA + PostgreSQL
- Spring WebSocket (STOMP + SockJS)
- Springdoc OpenAPI (Swagger)

**Frontend**
- React + TypeScript + Vite
- Zustand (gerenciamento de estado)
- SCSS
- STOMP.js + SockJS

**Infraestrutura**
- Docker + Docker Compose

---

## Como executar

```bash
git clone https://github.com/VictorCFerreira/BCB
cd BCB
docker-compose up --build
```


| Serviço  | URL                                         |
|----------|---------------------------------------------|
| Frontend | http://localhost:3000                       |
| Backend  | http://localhost:8080                       |
| Swagger  | http://localhost:8080/swagger-ui/index.html |

---

## Caminho feliz — como testar

### 1. Cadastrar dois usuários

Acesse http://localhost:3000 e clique em **Criar conta**.

Cadastre dois usuários com planos à sua escolha:
- Usuário A — ex: CPF `11111111111`, plano Pré-pago
- Usuário B — ex: CPF `22222222222`, plano Pós-pago

### 2. Logar em dois navegadores diferentes(No ex: Chrome + Firefox)

- Acesse http://localhost:3000 no **Chrome** e logue com o Usuário A
- Acesse http://localhost:3000 no **Firefox** (ou uma guia anônima) e logue com o Usuário B

### 3. Recarregar saldo / atualizar limite

Na sidebar inferior, clique no botão **$** ao lado do nome do usuário logado:
- Usuário pré-pago: informe um valor para recarregar o saldo
- Usuário pós-pago: informe um novo limite mensal

### 4. Enviar primeira mensagem

No Chrome (Usuário A), clique em **+** na sidebar e:
- Informe o documento do Usuário B (`22222222222`)
- Escolha a prioridade (Normal R$0,25 ou Urgente R$0,50)
- Digite a primeira mensagem e envie

A conversa aparecerá automaticamente na sidebar do Usuário B em até 5 segundos.

### 5. Testar o tempo real

- No Firefox (Usuário B), clique na conversa que apareceu e responda
- No Chrome (Usuário A), a mensagem aparece em tempo real via WebSocket

### 6. Testar validação financeira

Tente enviar mensagens até o saldo/limite acabar — um dialog de erro informará o valor disponível.

---

## Premissas assumidas

- O destinatário de uma mensagem precisa ser um cliente cadastrado no sistema
- Não há senha de acesso — o CPF ou CNPJ funciona como identificador único, conforme especificado no documento do desafio
- O custo da mensagem é debitado no momento do envio

---

## Decisões arquiteturais

### Modelo bidirecional de conversas
O documento original sugeria um modelo unidirecional onde o destinatário seria um contato externo. Optei pelo modelo bidirecional onde ambos os participantes são clientes cadastrados, permitindo que os dois lados visualizem e respondam a mesma conversa.

### Fila com suporte a prioridade
A fila de mensagens foi implementada com uma interface `MessageQueue`. A implementação ativa é a `PriorityMessageQueue` com `PriorityBlockingQueue` — mensagens urgentes são sempre processadas antes das normais dentro do mesmo ciclo do worker, e dentro do mesmo nível de prioridade a ordem é FIFO.

### Autenticação por documento
Não há senha — o CPF/CNPJ funciona como identificador único de acesso. O token JWT é gerado no login e usado para autenticar todas as requisições subsequentes.

### Reset mensal automático
O consumo mensal de clientes pós-pago é zerado automaticamente no primeiro envio de mensagem após a virada do mês, sem necessidade de job externo agendado.


---

## O que faria em projetos maiores

- **Migrações com Flyway** — versionamento do schema do banco com rollback controlado, substituindo o `ddl-auto: update` atual
- **Testes unitários** — cobertura do `PagamentoService` e `MensagemService`, garantindo as regras de negócio
- **Internacionalização com i18n** — suporte a múltiplos idiomas no frontend
- **Sistema de notificações via WebSocket** — notificar o cliente quando chegar mensagem em conversas que não estão abertas, com contador de não lidas na sidebar
- **Rate limiting** — limitar requisições por cliente para evitar abuso da API
- **Refresh token** — renovação automática do JWT sem precisar relogar
- **Estorno automático** — devolver o valor ao cliente em caso de falha no processamento da mensagem
- **Estratégia de retry** — retentar automaticamente mensagens com status FALHA
