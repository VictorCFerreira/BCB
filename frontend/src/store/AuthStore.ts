import { create } from 'zustand'
import { persist } from 'zustand/middleware'

interface ClienteLogado {
  clientId: number
  nome: string
  planType: 'PRE_PAGO' | 'POS_PAGO'
  saldo?: number
  limiteMensal?: number
  gastoMesAtual?: number
}

interface AuthState {
  token: string | null
  cliente: ClienteLogado | null
  login: (token: string, cliente: ClienteLogado) => void
  logout: () => void
  atualizarValor: (valor: number) => void
}

export const useAuthStore = create<AuthState>()(
  persist(
    set => ({
      token: null,
      cliente: null,
      login: (token, cliente) => set({ token, cliente }),
      logout: () => set({ token: null, cliente: null }),
      atualizarValor: (valor) => set(s => ({
        cliente: s.cliente ? { ...s.cliente,
          saldo: s.cliente.planType === 'PRE_PAGO' ? valor : s.cliente.saldo,
          gastoMesAtual: s.cliente.planType === 'POS_PAGO' ? valor : s.cliente.gastoMesAtual,
        } : null
      })),
    }),
    { name: 'bcb-auth' }
  )
)


