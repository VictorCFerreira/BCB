import { useAuthStore } from '../store/authStore'
import '../styles/cliente-info.scss'

export function ClienteInfo() {
  const { cliente } = useAuthStore()
  if (!cliente) return null

  const isPrePago = cliente.planType === 'PRE_PAGO'

  return (
    <div className="cliente-info">
      <div className="cliente-nome">{cliente.nome}</div>

      {isPrePago ? (
        <div className="cliente-saldo">
          <span className="info-label">Saldo</span>
          <span className="info-valor">
            R$ {cliente.saldo?.toFixed(2) ?? '0,00'}
          </span>
        </div>
      ) : (
        <div className="cliente-saldo">
          <span className="info-label">Mensal</span>
          <span className="info-valor">
            R$ {cliente.gastoMesAtual?.toFixed(2) ?? '0,00'}
            <span className="info-limite">
              {' '}/ R$ {cliente.limiteMensal?.toFixed(2) ?? '0,00'}
            </span>
          </span>
        </div>
      )}
    </div>
  )
}