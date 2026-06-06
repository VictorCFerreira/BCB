import { create } from 'zustand'
import { persist } from 'zustand/middleware'

interface AuthState {
  token: string | null
  clientId: string | null
  name: string | null
  login: (token: string, clientId: string, name: string) => void
  logout: () => void
}

export const useAuthStore = create<AuthState>()(
  persist(
    set => ({
      token: null,
      clientId: null,
      name: null,
      login: (token, clientId, name) => set({ token, clientId, name }),
      logout: () => set({ token: null, clientId: null, name: null }),
    }),
    { name: 'bcb-auth' }
  )
)