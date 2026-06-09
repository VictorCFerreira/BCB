import { useEffect, useState } from 'react'
import '../styles/sidebar.scss'
import { conversaService } from '../services/conversaService'
import { useChatStore } from '../store/chatStore'
import { NovaConversaModal } from './NovaConversaModal'

export function Sidebar() {
  const { conversas, conversaAtiva, setConversas, setConversaAtiva } = useChatStore()
  const [modalAberto, setModalAberto] = useState(false)

  useEffect(() => {
    conversaService.listar().then(setConversas)
  }, [])

  return (
    <>
      <div className={`sidebar ${conversaAtiva ? 'tem-conversa-ativa' : ''}`}>
        <div className="sidebar-header">
          <span>Conversas</span>
          <button
            className="btn-nova-conversa"
            onClick={() => setModalAberto(true)}
          >
            +
          </button>
        </div>

        {conversas.length === 0 ? (
          <p className="sidebar-empty">Nenhuma conversa ainda</p>
        ) : (
          conversas.map(c => (
            <div
              key={c.id}
              className={`conversa-item ${conversaAtiva?.id === c.id ? 'ativa' : ''}`}
              onClick={() => setConversaAtiva(c)}
            >
              <p className="conversa-nome">{c.nomeOutroParticipante}</p>
              <p className="conversa-doc">{c.documentoOutroParticipante}</p>
            </div>
          ))
        )}
      </div>

      {modalAberto && (
        <NovaConversaModal onClose={() => setModalAberto(false)} />
      )}
    </>
  )
}