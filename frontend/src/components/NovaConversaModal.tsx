import { useState } from 'react'
import '../styles/modal.scss'
import { mensagemService } from '../services/mensagemService'
import { useChatStore } from '../store/chatStore'
import { conversaService } from '../services/conversaService'

interface Props {
  onClose: () => void
}

export function NovaConversaModal({ onClose }: Props) {
  const [documento, setDocumento] = useState('')
  const [nome, setNome] = useState('')
  const [mensagem, setMensagem] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const { setConversas, setConversaAtiva } = useChatStore()

  const handleEnviar = async () => {
    if (!documento.trim() || !nome.trim() || !mensagem.trim()) {
      setError('Preencha todos os campos')
      return
    }

    setLoading(true)
    setError('')

    try {
      await mensagemService.enviarNova(documento.trim(), nome.trim(), mensagem.trim())

      const conversas = await conversaService.listar()
      setConversas(conversas)

      const nova = conversas.find(c => c.documentoDestinatario === documento.trim())
      if (nova) setConversaAtiva(nova)

      onClose()
    } catch (e: any) {
      setError(e.message ?? 'Erro ao enviar mensagem')
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
        <p className="modal-title">Nova conversa</p>

        <div className="modal-field">
          <label>Documento do destinatário (CPF/CNPJ)</label>
          <input
            type="text"
            placeholder="Só números"
            value={documento}
            onChange={e => setDocumento(e.target.value)}
          />
        </div>

        <div className="modal-field">
          <label>Nome do destinatário</label>
          <input
            type="text"
            placeholder="Nome"
            value={nome}
            onChange={e => setNome(e.target.value)}
          />
        </div>

        <div className="modal-field">
          <label>Primeira mensagem</label>
          <textarea
            rows={3}
            placeholder="Digite sua mensagem..."
            value={mensagem}
            onChange={e => setMensagem(e.target.value)}
          />
        </div>

        {error && <p className="modal-error">{error}</p>}

        <div className="modal-actions">
          <button className="btn-cancelar" onClick={onClose}>
            Cancelar
          </button>
          <button className="btn-enviar" onClick={handleEnviar} disabled={loading}>
            {loading ? 'Enviando...' : 'Enviar'}
          </button>
        </div>
      </div>
    </div>
  )
}