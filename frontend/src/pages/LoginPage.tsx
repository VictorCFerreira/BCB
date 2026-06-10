import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import '../styles/login.scss'
import { api } from '../api/Cliente'
import { useAuthStore } from '../store/authStore'

export default function LoginPage() {
  const [documento, setDocumento] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const { login } = useAuthStore()
  const navigate = useNavigate()

  const handleSubmit = async () => {
    setLoading(true)
    setError('')
    try {
      const data = await api.post<any>('/auth/login', { documento })
      login(data.token, {
        clientId: data.clientId,
        nome: data.nome,
        planType: data.planType,
        saldo: data.saldo,
        limiteMensal: data.limiteMensal,
        gastoMesAtual: data.gastoMesAtual,
      })
      navigate('/chat')
    } catch (e: any) {
      setError(e.message ?? 'Erro ao fazer login')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="login-page">
      <div className="login-card">
        <h2 className="login-title">BCB</h2>
        <p className="login-subtitle">Digite seu CPF ou CNPJ para entrar</p>

        <input
          type="text"
          placeholder="CPF ou CNPJ (só números)"
          value={documento}
          onChange={e => setDocumento(e.target.value)}
          onKeyDown={e => e.key === 'Enter' && handleSubmit()}
        />

        {error && <p className="login-error">{error}</p>}

        <button onClick={handleSubmit} disabled={loading}>
          {loading ? 'Entrando...' : 'Entrar'}
        </button>
        <button onClick={() => navigate('/cadastro')}>
          Criar conta
        </button>

      </div>
    </div>
  )
}