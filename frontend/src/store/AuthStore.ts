import { create } from 'zustand'
import { persist } from 'zustand/middleware'

interface ClienteLogado {
  clientId: number
  nome: string
  planType: string
  saldo: number
}

interface AuthState {
  token: string | null
  cliente: ClienteLogado | null
  login: (token: string, cliente: ClienteLogado) => void
  logout: () => void
}

export const useAuthStore = create<AuthState>()(
  persist(
    set => ({
      token: null,
      cliente: null,
      login: (token, cliente) => set({ token, cliente }),
      logout: () => set({ token: null, cliente: null }),
    }),
    { name: 'bcb-auth' }
  )
)