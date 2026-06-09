import { useState } from 'react'
import '../styles/chat.scss'

interface Props {
  onSend: (conteudo: string) => Promise<void>
}

export function MessageInput({ onSend }: Props) {
  const [conteudo, setConteudo] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSend = async () => {
    if (!conteudo.trim() || loading) return
    setLoading(true)
    try {
      await onSend(conteudo.trim())
      setConteudo('')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="message-input">
      <input
        type="text"
        placeholder="Digite uma mensagem..."
        value={conteudo}
        onChange={e => setConteudo(e.target.value)}
        onKeyDown={e => e.key === 'Enter' && handleSend()}
        disabled={loading}
      />
      <button onClick={handleSend} disabled={loading || !conteudo.trim()}>
        ➤
      </button>
    </div>
  )
}