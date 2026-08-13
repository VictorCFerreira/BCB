import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import '../styles/cadastro.scss'
import { clienteService } from '../services/clienteService'

export default function CadastroPage() {
  const [nome, setNome] = useState('')
  const [documento, setDocumento] = useState('')
  const [plano, setPlano] = useState<'PRE_PAGO' | 'POS_PAGO'>('PRE_PAGO')
  const [limiteMensal, setLimiteMensal] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const navigate = useNavigate()

  const handleCadastrar = async () => {
    if (!nome.trim() || !documento.trim()) {
      setError('Please fill in name and document')
      return
    }
  
    if (plano === 'POS_PAGO' && !limiteMensal) {
      setError('Please enter the monthly limit for a postpaid plan')
      return
    }
  
    setLoading(true)
    setError('')
  
    try {
      await clienteService.cadastrar({
        nome: nome.trim(),
        documento: documento.trim(),
        plano,
        limiteMensal: plano === 'POS_PAGO' ? Number(limiteMensal) : undefined,
      })
  
      navigate('/login')
    } catch (e: any) {
      setError(e.message ?? 'Failed to register')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="cadastro-page">
      <div className="cadastro-card">
        <h2 className="cadastro-title">BCB</h2>
        <p className="cadastro-subtitle">Create new account</p>

        <div className="campo">
          <label>Name</label>
          <input
            type="text"
            placeholder="Your full name"
            value={nome}
            onChange={e => setNome(e.target.value)}
          />
        </div>

        <div className="campo">
          <label>CPF or CNPJ (numbers only)</label>
          <input
            type="text"
            placeholder="Document"
            value={documento}
            onChange={e => setDocumento(e.target.value)}
          />
        </div>

        <div className="campo">
          <label>Plan type</label>
          <select
            value={plano}
            onChange={e => setPlano(e.target.value as 'PRE_PAGO' | 'POS_PAGO')}
          >
            <option value="PRE_PAGO">Prepaid</option>
            <option value="POS_PAGO">Postpaid</option>
          </select>
        </div>

        {plano === 'POS_PAGO' && (
          <div className="campo">
            <label>Monthly limit (R$)</label>
            <input
              type="number"
              placeholder="Ex: 100.00"
              value={limiteMensal}
              onChange={e => setLimiteMensal(e.target.value)}
            />
          </div>
        )}

        {error && <p className="cadastro-error">{error}</p>}

        <button
          className="btn-cadastrar"
          onClick={handleCadastrar}
          disabled={loading}
        >
          {loading ? 'Registering...' : 'Register'}
        </button>

        <button className="btn-voltar" onClick={() => navigate('/login')}>
          I already have an account
        </button>
      </div>
    </div>
  )
}