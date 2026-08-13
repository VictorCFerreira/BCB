import { useState } from 'react'
import '../styles/modal.scss'
import { mensagemService } from '../services/mensagemService'
import { useChatStore } from '../store/chatStore'
import { conversaService } from '../services/conversaService'
import type { Prioridade } from '../types'

interface Props {
  onClose: () => void
}

export function NovaConversaModal({ onClose }: Props) {
  const [documento, setDocumento] = useState('')
  const [mensagem, setMensagem] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const [prioridade, setPrioridade] = useState<Prioridade>('NORMAL')


  const { setConversas, setConversaAtiva } = useChatStore()




  const handleEnviar = async () => {
    if (!documento.trim() || !mensagem.trim()) {
      setError('Please fill in all fields')
      return
    }

    setLoading(true)
    setError('')

    try {
      await mensagemService.enviarNova(documento.trim(), mensagem.trim(), prioridade)

      const conversas = await conversaService.listar()
      setConversas(conversas)

      const nova = conversas.find(c => c.documentoOutroParticipante === documento.trim())
      if (nova) setConversaAtiva(nova)

      onClose()
    } catch (e: any) {
      setError(e.message ?? 'Failed to send message')
    } finally {
      setLoading(false)
    }
  }

  const handleOverlayClick = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.target === e.currentTarget) onClose()
  }

  return (
    <div className="modal-overlay" onClick={handleOverlayClick}>
      <div className="modal">
        <p className="modal-title">New conversation</p>

        <div className="modal-field">
          <label>Recipient document (CPF/CNPJ)</label>
          <input
            type="text"
            placeholder="Numbers only"
            value={documento}
            onChange={e => setDocumento(e.target.value)}
          />
        </div>


        <div className="modal-field">
          <label>First message</label>
          <textarea
            rows={3}
            placeholder="Type your message..."
            value={mensagem}
            onChange={e => setMensagem(e.target.value)}
          />
        </div>

        <div className="modal-field">
          <label>Priority</label>
          <select
            value={prioridade}
            onChange={e => setPrioridade(e.target.value as Prioridade)}
          >
            <option value="NORMAL">Normal · R$0.25</option>
            <option value="URGENTE">Urgent · R$0.50</option>
          </select>
        </div>

        {error && <p className="modal-error">{error}</p>}

        <div className="modal-actions">
          <button className="btn-cancelar" onClick={onClose}>
            Cancel
          </button>
          <button className="btn-enviar" onClick={handleEnviar} disabled={loading}>
            {loading ? 'Sending...' : 'Send'}
          </button>
        </div>
      </div>
    </div>
  )
}