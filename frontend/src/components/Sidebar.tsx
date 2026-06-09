import { useEffect } from 'react'
import '../styles/sidebar.scss'
import { conversaService } from '../services/conversaService'
import { useChatStore } from '../store/chatStore'

export function Sidebar() {
  const { conversas, conversaAtiva, setConversas, setConversaAtiva } = useChatStore()

  useEffect(() => {
    conversaService.listar().then(setConversas)
  }, [])

  return (
    <div className={`sidebar ${conversaAtiva ? 'tem-conversa-ativa' : ''}`}>
      <div className="sidebar-header">Conversas</div>

      {conversas.length === 0 ? (
        <p className="sidebar-empty">Nenhuma conversa ainda</p>
      ) : (
        conversas.map(c => (
          <div
            key={c.id}
            className={`conversa-item ${conversaAtiva?.id === c.id ? 'ativa' : ''}`}
            onClick={() => setConversaAtiva(c)}
          >
            <p className="conversa-nome">{c.nomeDestinatario}</p>
            <p className="conversa-doc">{c.documentoDestinatario}</p>
          </div>
        ))
      )}
    </div>
  )
}