import { api } from '../api/client'
import type { Conversa } from '../types'

export const conversaService = {
  listar: () => api.get<Conversa[]>('/conversas'),
}