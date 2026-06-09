import { useEffect, useRef } from 'react'
import '../styles/chat.scss'
import { mensagemService } from '../services/mensagemService'
import { useChatStore } from '../store/chatStore'
import { MessageBubble } from './MessageBubble'
import { MessageInput } from './MessageInput'

export function ChatWindow() {
  const { conversaAtiva, mensagens, setMensagens, appendMensagem } = useChatStore()
  const bottomRef = useRef<HTMLDivElement>(null)
  const msgs = conversaAtiva ? (mensagens[conversaAtiva.id] ?? []) : []

  useEffect(() => {
    if (!conversaAtiva) return
    mensagemService.listarPorConversa(conversaAtiva.id)
      .then(m => setMensagens(conversaAtiva.id, m))
  }, [conversaAtiva?.id])

  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [msgs.length])

  const handleSend = async (conteudo: string) => {
    if (!conversaAtiva) return
    const nova = await mensagemService.enviar(conversaAtiva.id, conteudo)
    appendMensagem(conversaAtiva.id, nova)
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