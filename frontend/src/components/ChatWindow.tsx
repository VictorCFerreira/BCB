import { useEffect, useRef } from 'react'
import '../styles/chat.scss'
import { mensagemService } from '../services/mensagemService'
import { useChatStore } from '../store/chatStore'
import { MessageBubble } from './MessageBubble'
import { MessageInput } from './MessageInput'
import { useAuthStore } from '../store/authStore'
import { conectar, desconectar } from '../services/webSocketService'

export function ChatWindow() {
  const { conversaAtiva, mensagens, setMensagens, appendMensagem } = useChatStore()
  const { atualizarValor } = useAuthStore()
  const bottomRef = useRef<HTMLDivElement>(null)
  const msgs = conversaAtiva ? (mensagens[conversaAtiva.id] ?? []) : []

  useEffect(() => {
    if (!conversaAtiva) return

    mensagemService.listarPorConversa(conversaAtiva.id)
      .then(m => setMensagens(conversaAtiva.id, m))

    conectar(conversaAtiva.id, (novaMensagem) => {
      const msgs = useChatStore.getState().mensagens[conversaAtiva.id] ?? []
      const jaExiste = msgs.some(m => m.id === novaMensagem.id)

      if (jaExiste) {
        useChatStore.getState().atualizarMensagem(conversaAtiva.id, novaMensagem)
      } else {
        useChatStore.getState().appendMensagem(conversaAtiva.id, novaMensagem)
      }
    })

    return () => { desconectar() }
  }, [conversaAtiva?.id])

  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [msgs.length])

  const handleSend = async (conteudo: string) => {
    if (!conversaAtiva) return
    const nova = await mensagemService.enviar(conversaAtiva.id, conteudo)
    appendMensagem(conversaAtiva.id, nova);
    atualizarValor(nova.valorAtualizado)
  }

  if (!conversaAtiva) {
    return (
      <div className="chat-window">
        <div className="chat-empty">Selecione uma conversa</div>
      </div>
    )
  }

  return (
    <div className="chat-window">
      <div className="chat-header">
        <div className="chat-header-name">{conversaAtiva.nomeOutroParticipante}</div>
        <div className="chat-header-doc">{conversaAtiva.documentoOutroParticipante}</div>
      </div>

      <div className="chat-messages">
        {msgs.map(m => <MessageBubble key={m.id} mensagem={m} />)}
        <div ref={bottomRef} />
      </div>

      <MessageInput onSend={handleSend} />
    </div>
  )
}