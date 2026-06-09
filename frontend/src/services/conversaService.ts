import { api } from '../api/Cliente'
import type { Conversa } from '../types'

export const conversaService = {
  listar: () => api.get<Conversa[]>('/conversas'),
}