export interface Conversa {
  id: number
  nomeOutroParticipante: string
  documentoOutroParticipante: string
  criadaEm: string
}

export interface Mensagem {
  id: number
  conteudo: string
  status: 'ENFILEIRADA' | 'PROCESSANDO' | 'ENVIADA' | 'FALHA'
  custo: number
  criadaEm: string
  remetenteId: number
}