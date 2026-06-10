import { api } from '../api/Cliente'
import type { AuthResponse } from '../types'

export const clienteService = {
  cadastrar: (dados: {
    nome: string
    documento: string
    plano: 'PRE_PAGO' | 'POS_PAGO'
    limiteMensal?: number
  }) => api.post<AuthResponse>('/auth/register', dados),
}