import { api } from "../api/client"

interface ClienteFinanceiroResponse {
    saldo?: number
    limiteMensal?: number
    gastoMesAtual?: number
  }
  
  export const pagamentoService = {
    recarregar: (valor: number) =>
      api.post<ClienteFinanceiroResponse>('/pagamento/recarregar', { valor }),
  
    atualizarLimite: (novoLimite: number) =>
      api.put<ClienteFinanceiroResponse>('/pagamento/limite', { novoLimite }),
  }