import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import '../styles/login.scss'
import { api } from '../api/client'
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
      setError(e.message ?? 'Failed to sign in')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="login-page">
      <div className="login-card">
        <h2 className="login-title">BCB</h2>
        <p className="login-subtitle">Enter your CPF or CNPJ to sign in</p>

        <input
          type="text"
          placeholder="CPF or CNPJ (numbers only)"
          value={documento}
          onChange={e => setDocumento(e.target.value)}
          onKeyDown={e => e.key === 'Enter' && handleSubmit()}
        />

        {error && <p className="login-error">{error}</p>}

        <button onClick={handleSubmit} disabled={loading}>
          {loading ? 'Signing in...' : 'Sign in'}
        </button>
        <button onClick={() => navigate('/cadastro')}>
          Create account
        </button>

      </div>
    </div>
  )
}