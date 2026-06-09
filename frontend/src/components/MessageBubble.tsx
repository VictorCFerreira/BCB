import '../styles/chat.scss'
import type { Mensagem } from '../types'

const statusIcon: Record<Mensagem['status'], string> = {
  ENFILEIRADA: 'E',
  PROCESSANDO: 'P',
  ENVIADA: '✓',
  FALHA: '✗',
}

export function MessageBubble({ mensagem }: { mensagem: Mensagem }) {
  return (
    <div className="message-bubble">
      <div className="bubble">
        <p className="bubble-text">{mensagem.conteudo}</p>
        <p className="bubble-status">{statusIcon[mensagem.status]}</p>
      </div>
    </div>
  )
}