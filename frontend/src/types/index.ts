export interface Conversa {
    id: number
    nomeDestinatario: string
    documentoDestinatario: string
    criadaEm: string
  }
  
  export interface Mensagem {
    id: number
    conteudo: string
    status: 'ENFILEIRADA' | 'PROCESSANDO' | 'ENVIADA' | 'FALHA'
    custo: number
    criadaEm: string
  }