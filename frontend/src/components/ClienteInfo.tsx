import { useState } from 'react'
import { useAuthStore } from '../store/authStore'
import { pagamentoService } from '../services/pagamentoService'
import '../styles/cliente-info.scss'
import '../styles/modal.scss'

export function ClienteInfo() {
  const { cliente, atualizarValor } = useAuthStore()
  const [modalAberto, setModalAberto] = useState(false)
  const [valor, setValor] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  if (!cliente) return null

  const isPrePago = cliente.planType === 'PRE_PAGO'

  const handleConfirmar = async () => {
    const num = Number(valor)
    if (!valor || isNaN(num) || num <= 0) {
      setError('Please enter a valid amount greater than zero')
      return
    }

    setLoading(true)
    setError('')

    try {
      if (isPrePago) {
        const data = await pagamentoService.recarregar(num)
        atualizarValor(data.saldo!)
      } else {
        const data = await pagamentoService.atualizarLimite(num)
        useAuthStore.getState().atualizarLimite(data.limiteMensal!)
      }
      setValor('')
      setModalAberto(false)
    } catch (e: any) {
      setError(e.message ?? 'Failed to process')
    } finally {
      setLoading(false)
    }
  }

  const handleOverlayClick = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.target === e.currentTarget) {
      setModalAberto(false)
      setError('')
      setValor('')
    }
  }

  return (
    <>
      <div className="cliente-info">
        <div className="cliente-header">
          <span className="cliente-nome">{cliente.nome}</span>
          <button
            className="btn-financeiro"
            onClick={() => setModalAberto(true)}
            title={isPrePago ? 'Top up balance' : 'Update limit'}
          >
            $
          </button>
        </div>

        {isPrePago ? (
          <div className="cliente-saldo">
            <span className="info-label">Balance</span>
            <span className="info-valor">
              R$ {cliente.saldo?.toFixed(2) ?? '0.00'}
            </span>
          </div>
        ) : (
          <div className="cliente-saldo">
            <span className="info-label">Monthly</span>
            <span className="info-valor">
              R$ {cliente.gastoMesAtual?.toFixed(2) ?? '0.00'}
              <span className="info-limite">
                {' '}/ R$ {cliente.limiteMensal?.toFixed(2) ?? '0.00'}
              </span>
            </span>
          </div>
        )}
      </div>

      {modalAberto && (
        <div className="modal-overlay" onClick={handleOverlayClick}>
          <div className="modal">
            <p className="modal-title">
              {isPrePago ? 'Top up balance' : 'Update monthly limit'}
            </p>

            <div className="modal-field">
              <label>{isPrePago ? 'Top-up amount (R$)' : 'New monthly limit (R$)'}</label>
              <input
                type="number"
                placeholder="Ex: 50.00"
                value={valor}
                onChange={e => setValor(e.target.value)}
                min="0.01"
                step="0.01"
              />
            </div>

            {error && <p className="modal-error">{error}</p>}

            <div className="modal-actions">
              <button
                className="btn-cancelar"
                onClick={() => {
                  setModalAberto(false)
                  setError('')
                  setValor('')
                }}
              >
                Cancel
              </button>
              <button
                className="btn-enviar"
                onClick={handleConfirmar}
                disabled={loading}
              >
                {loading ? 'Processing...' : 'Confirm'}
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  )
}