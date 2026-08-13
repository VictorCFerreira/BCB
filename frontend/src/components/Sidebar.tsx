import { useEffect, useState } from 'react'
import '../styles/sidebar.scss'
import { conversaService } from '../services/conversaService'
import { useChatStore } from '../store/chatStore'
import { NovaConversaModal } from './NovaConversaModal'
import { ClienteInfo } from './ClienteInfo'

export function Sidebar() {
  const { conversas, conversaAtiva, setConversas, setConversaAtiva } = useChatStore()
  const [modalAberto, setModalAberto] = useState(false)

  useEffect(() => {
    conversaService.listar().then(setConversas)
  
    const interval = setInterval(() => {
      conversaService.listar().then(setConversas)
    }, 5000)
  
    return () => clearInterval(interval)
  }, [])


  return (
    <>
      <div className={`sidebar ${conversaAtiva ? 'tem-conversa-ativa' : ''}`}>
        <div className="sidebar-header">
          <span>Conversations</span>
          <button className="btn-nova-conversa" onClick={() => setModalAberto(true)}>
            +
          </button>
        </div>
  
        <div className="sidebar-lista"> 
          {conversas.length === 0 ? (
            <p className="sidebar-empty">No conversations yet</p>
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
  
        <ClienteInfo />
      </div>
  
      {modalAberto && <NovaConversaModal onClose={() => setModalAberto(false)} />}
    </>
  )
}