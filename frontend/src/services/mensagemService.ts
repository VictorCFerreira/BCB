import { api } from '../api/client'
import type { Mensagem, Prioridade } from '../types'

export const mensagemService = {
  listarPorConversa: (conversaId: number) =>
    api.get<Mensagem[]>(`/mensagens/conversa/${conversaId}`),

  enviar: (conversaId: number, conteudo: string, prioridade: Prioridade) =>
    api.post<Mensagem>('/mensagens', { conversaId, conteudo, prioridade }),
  
  enviarNova: (documentoDestinatario: string, conteudo: string, prioridade: Prioridade) =>
    api.post<Mensagem>('/mensagens', { documentoDestinatario, conteudo, prioridade }),
}