import { useAuthStore } from '../store/AuthStore'
import '../styles/chat.scss'
import type { Mensagem } from '../types'

const statusIcon: Record<Mensagem['status'], string> = {
  ENFILEIRADA: 'E',
  PROCESSANDO: 'P',
  ENVIADA: '✓',
  FALHA: '✗',
}



export function MessageBubble({ mensagem }: { mensagem: Mensagem }) {
  const { cliente } = useAuthStore()
  const isEnviada = mensagem.remetenteId === cliente?.clientId

  return (
    <div className="message-bubble" style={{
      justifyContent: isEnviada ? 'flex-end' : 'flex-start'
    }}>
      <div className="bubble" style={{
        background: isEnviada ? '#dcf8c6' : 'white',
        borderRadius: isEnviada ? '12px 12px 0 12px' : '12px 12px 12px 0',
        border: isEnviada ? 'none' : '1px solid #e0e0e0',
      }}>
        <p className="bubble-text">{mensagem.conteudo}</p>
        <p className="bubble-status">
          {isEnviada ? statusIcon[mensagem.status] : ''}
        </p>
      </div>
    </div>
  )
}