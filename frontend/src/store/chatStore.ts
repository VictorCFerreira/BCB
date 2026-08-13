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
  atualizarMensagem: (conversaId: number, mensagem: Mensagem) => void
  upsertMensagem: (conversaId: number, mensagem: Mensagem) => void
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
    atualizarMensagem: (conversaId: number, mensagem: Mensagem) =>
      set(s => ({
        mensagens: {
          ...s.mensagens,
          [conversaId]: s.mensagens[conversaId].map(m =>
            m.id === mensagem.id ? mensagem : m
          ),
        },
      })),
      upsertMensagem: (conversaId: number, mensagem: Mensagem) =>
        set(s => {
          const atuais = s.mensagens[conversaId] ?? []
          const existe = atuais.some(m => m.id === mensagem.id)
          return {
            mensagens: {
              ...s.mensagens,
              [conversaId]: existe
                ? atuais.map(m => (m.id === mensagem.id ? mensagem : m))
                : [...atuais, mensagem],
            },
          }
        }),
}))