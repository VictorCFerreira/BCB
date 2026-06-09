import { api } from '../api/Cliente'
import type { Mensagem } from '../types'

export const mensagemService = {
  listarPorConversa: (conversaId: number) =>
    api.get<Mensagem[]>(`/mensagens/conversa/${conversaId}`),

  enviar: (conversaId: number, conteudo: string) =>
    api.post<Mensagem>('/mensagens', { conversaId, conteudo }),

  enviarNova: (documentoDestinatario: string, nomeDestinatario: string, conteudo: string) =>
    api.post<Mensagem>('/mensagens', { documentoDestinatario, nomeDestinatario, conteudo }),
}