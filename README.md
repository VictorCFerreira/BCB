# BCB — Big Chat Brasil

A chat platform for communication between clients, with support for prepaid and postpaid plans, a priority message queue, and real-time communication via WebSocket.

Developed by **Victor**.

---

## Tech stack

**Backend**
- Java 21 + Spring Boot
- Spring Security + JWT
- Spring Data JPA + PostgreSQL
- Spring WebSocket (STOMP + SockJS)
- Springdoc OpenAPI (Swagger)

**Frontend**
- React + TypeScript + Vite
- Zustand (state management)
- SCSS
- STOMP.js + SockJS

**Infrastructure**
- Docker + Docker Compose

---

## Getting started

```bash
git clone https://github.com/VictorCFerreira/BCB
cd BCB
docker-compose up --build
```

| Service  | URL                                         |
|----------|---------------------------------------------|
| Frontend | http://localhost:3000                       |
| Backend  | http://localhost:8080                       |
| Swagger  | http://localhost:8080/swagger-ui/index.html |

---

## Happy path — how to try it

### 1. Register two users

Open http://localhost:3000 and click **Create account**.

Register two users with plans of your choice:
- User A — e.g. CPF `11111111111`, Prepaid plan
- User B — e.g. CPF `22222222222`, Postpaid plan

### 2. Sign in on two different browsers (e.g. Chrome + Firefox)

- Open http://localhost:3000 in **Chrome** and sign in as User A
- Open http://localhost:3000 in **Firefox** (or a private window) and sign in as User B

### 3. Top up balance / update limit

In the bottom sidebar, click the **$** button next to the signed-in user's name:
- Prepaid user: enter an amount to top up the balance
- Postpaid user: enter a new monthly limit

### 4. Send the first message

In Chrome (User A), click **+** in the sidebar and:
- Enter User B's document (`22222222222`)
- Choose a priority (Normal R$0.25 or Urgent R$0.50)
- Type the first message and send it

The conversation will appear automatically in User B's sidebar within 5 seconds.

### 5. Test real-time messaging

- In Firefox (User B), open the conversation that appeared and reply
- In Chrome (User A), the message appears in real time via WebSocket

### 6. Test billing validation

Keep sending messages until the balance or limit runs out — an error dialog will show the available amount.

---

## Assumptions

- The recipient of a message must be a registered client in the system
- There is no password — CPF or CNPJ acts as the unique identifier for sign-in
- Message cost is charged at send time

---

## Architecture decisions

### Bidirectional conversation model

Both participants are registered clients, so each side can view and reply in the same conversation.

### Priority message queue

The message queue is built behind a `MessageQueue` interface. The active implementation is `PriorityMessageQueue` with a `PriorityBlockingQueue` — urgent messages are always processed before normal ones within the same worker cycle, and within the same priority level the order is FIFO.

### Document-based authentication

There is no password — CPF/CNPJ is the unique access identifier. A JWT is issued at sign-in and used to authenticate all subsequent requests.

### Automatic monthly reset

Monthly usage for postpaid clients is reset automatically on the first message sent after the month changes, without an external scheduled job.

---

## What I would add at larger scale

- **Flyway migrations** — versioned database schema with controlled rollbacks, replacing the current `ddl-auto: update`
- **Unit tests** — coverage for `PagamentoService` and `MensagemService` to lock in business rules
- **i18n** — multi-language support in the frontend
- **WebSocket notifications** — notify clients when a message arrives in a conversation that is not open, with unread counts in the sidebar
- **Rate limiting** — cap requests per client to reduce API abuse
- **Refresh tokens** — renew JWTs automatically without signing in again
- **Automatic refunds** — return the charge to the client if message processing fails
- **Retry strategy** — automatically retry messages with `FALHA` status
