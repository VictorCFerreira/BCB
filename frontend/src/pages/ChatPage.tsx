import { useNavigate } from 'react-router-dom'
import '../styles/chat.scss'
import { useAuthStore } from '../store/AuthStore'
import { Sidebar } from '../components/Sidebar'
import { ChatWindow } from '../components/ChatWindow'

export default function ChatPage() {
  const { cliente, logout } = useAuthStore()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  return (
    <div className="chat-page">
      <div className="topbar">
        <span className="topbar-title">BCB</span>
        <span className="topbar-user">
          {cliente?.nome}
        </span>
        <button className="topbar-logout" onClick={handleLogout}>
          Sair
        </button>
      </div>

      <div className="chat-body">
        <Sidebar />
        <ChatWindow />
      </div>
    </div>
  )
}