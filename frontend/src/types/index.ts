export interface Conversa {
  id: number
  nomeOutroParticipante: string
  documentoOutroParticipante: string
  criadaEm: string
}

export type Prioridade = 'NORMAL' | 'URGENTE'

export interface Mensagem {
  id: number
  conteudo: string
  prioridade: Prioridade
  status: 'ENFILEIRADA' | 'PROCESSANDO' | 'ENVIADA' | 'FALHA'
  custo: number
  criadaEm: string
  remetenteId: number
  valorAtualizado: number
}

export interface AuthResponse {
  token: string
  clientId: number
  nome: string
  planType: 'PRE_PAGO' | 'POS_PAGO'
  saldo?: number
  limiteMensal?: number
  gastoMesAtual?: number
}