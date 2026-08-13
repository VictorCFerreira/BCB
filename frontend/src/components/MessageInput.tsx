import { useState } from 'react'
import '../styles/chat.scss'
import type { Prioridade } from '../types'

interface Props {
  onSend: (conteudo: string, prioridade: Prioridade) => Promise<void>
}

export function MessageInput({ onSend }: Props) {
  const [conteudo, setConteudo] = useState('')
  const [prioridade, setPrioridade] = useState<Prioridade>('NORMAL')
  const [loading, setLoading] = useState(false)

  const handleSend = async () => {
    if (!conteudo.trim() || loading) return
    setLoading(true)
    try {
      await onSend(conteudo.trim(), prioridade)
      setConteudo('')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="message-input">
      <select
        value={prioridade}
        onChange={e => setPrioridade(e.target.value as Prioridade)}
        className="select-prioridade"
      >
        <option value="NORMAL">Normal · R$0.25</option>
        <option value="URGENTE">Urgent · R$0.50</option>
      </select>
      <input
        type="text"
        placeholder="Type a message..."
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