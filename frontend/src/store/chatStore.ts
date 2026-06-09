import { create } from 'zustand'
import type { Conversa, Mensagem } from '../types'

interface ChatState {
  conversas: Conversa[]
  conversaAtiva: Conversa | null
  mensagens: Record<number, Mensagem[]>
  setConversas: (conversas: Conversa[]) => void
  setConversaAtiva: (conversa: Conversa) => void
  setMensagens: (conversaId: number, mensagens: Mensagem[]) => void
  appendMensagem: (conversaId: number, mensagem: Mensagem) => void
}

export const useChatStore = create<ChatState>()(set => ({
  conversas: [],
  conversaAtiva: null,
  mensagens: {},
  setConversas: conversas => set({ conversas }),
  setConversaAtiva: conversa => set({ conversaAtiva: conversa }),
  setMensagens: (id, mensagens) =>
    set(s => ({ mensagens: { ...s.mensagens, [id]: mensagens } })),
  appendMensagem: (id, mensagem) =>
    set(s => ({
      mensagens: {
        ...s.mensagens,
        [id]: [...(s.mensagens[id] ?? []), mensagem],
      },
    })),
}))