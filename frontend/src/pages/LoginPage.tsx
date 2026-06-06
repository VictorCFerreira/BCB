import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import {api} from '../api/Cliente'
import { useAuthStore } from '../store/AuthStore'

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
      const { data } = await api.post('/auth/login', { documento })
      login(data.token, data.clientId, data.name)
      navigate('/chat')
    } catch (e: any) {
      setError(e.response?.data?.message ?? 'CPF/CNPJ não encontrado')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div style={{ maxWidth: 360, margin: '80px auto', padding: 24 }}>
      <h2>BCB — Identificação</h2>
      <input
        type="text"
        placeholder="CPF ou CNPJ (só números)"
        value={documento}
        onChange={e => setDocumento(e.target.value)}
        style={{ width: '100%', marginBottom: 12 }}
      />
      {error && <p style={{ color: 'red', fontSize: 13 }}>{error}</p>}
      <button onClick={handleSubmit} disabled={loading}>
        {loading ? 'Entrando...' : 'Entrar'}
      </button>
    </div>
  )
}