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
      setError('Preencha nome e documento')
      return
    }
  
    if (plano === 'POS_PAGO' && !limiteMensal) {
      setError('Informe o limite mensal para plano pós-pago')
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
      setError(e.message ?? 'Erro ao cadastrar')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="cadastro-page">
      <div className="cadastro-card">
        <h2 className="cadastro-title">BCB</h2>
        <p className="cadastro-subtitle">Criar nova conta</p>

        <div className="campo">
          <label>Nome</label>
          <input
            type="text"
            placeholder="Seu nome completo"
            value={nome}
            onChange={e => setNome(e.target.value)}
          />
        </div>

        <div className="campo">
          <label>CPF ou CNPJ (só números)</label>
          <input
            type="text"
            placeholder="Documento"
            value={documento}
            onChange={e => setDocumento(e.target.value)}
          />
        </div>

        <div className="campo">
          <label>Tipo de plano</label>
          <select
            value={plano}
            onChange={e => setPlano(e.target.value as 'PRE_PAGO' | 'POS_PAGO')}
          >
            <option value="PRE_PAGO">Pré-pago</option>
            <option value="POS_PAGO">Pós-pago</option>
          </select>
        </div>

        {plano === 'POS_PAGO' && (
          <div className="campo">
            <label>Limite mensal (R$)</label>
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
          {loading ? 'Cadastrando...' : 'Cadastrar'}
        </button>

        <button className="btn-voltar" onClick={() => navigate('/login')}>
          Já tenho conta
        </button>
      </div>
    </div>
  )
}