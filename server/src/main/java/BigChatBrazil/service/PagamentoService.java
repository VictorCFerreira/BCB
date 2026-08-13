package BigChatBrazil.service;

import BigChatBrazil.Enum.PlanoEnum;
import BigChatBrazil.Enum.PrioridadeEnum;
import BigChatBrazil.Exceptions.SemSaldoLimiteException;
import BigChatBrazil.domain.Cliente;
import BigChatBrazil.domain.DTO.Response.ClienteMudancaFinanceiroResponse;
import BigChatBrazil.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class PagamentoService {

    private static final Double CUSTO_NORMAL = 0.25;
    private static final Double CUSTO_URGENTE = 0.50;

    private final ClienteRepository clienteRepository;

    public Double calcularCusto(PrioridadeEnum prioridade) {
        return prioridade == PrioridadeEnum.URGENTE ? CUSTO_URGENTE : CUSTO_NORMAL;
    }

    public Double cobrar(Cliente cliente, PrioridadeEnum prioridade) {
        resetarGastoSeNecessario(cliente);
        Double custo = calcularCusto(prioridade);

        if (cliente.getPlano() == PlanoEnum.PRE_PAGO) {
            return cobrarPrePago(cliente, custo);
        } else {
            return cobrarPosPago(cliente, custo);
        }
    }

    private void resetarGastoSeNecessario(Cliente cliente) {
        if (cliente.getPlano() != PlanoEnum.POS_PAGO) return;
        if (cliente.getUltimoResetMensal() == null ||
                cliente.getUltimoResetMensal().getMonth() != LocalDateTime.now().getMonth()) {
            cliente.setGastoMesAtual(0.0);
            cliente.setUltimoResetMensal(LocalDateTime.now());
            clienteRepository.save(cliente);
        }
    }

    private Double cobrarPrePago(Cliente cliente, Double custo) {
        if (cliente.getSaldo() < custo) {
            throw new SemSaldoLimiteException(
                    "Insufficient balance. Current balance: R$" + String.format("%.2f", cliente.getSaldo()));
        }
        cliente.setSaldo(cliente.getSaldo() - custo);
        clienteRepository.save(cliente);
        return cliente.getSaldo();
    }

    private Double cobrarPosPago(Cliente cliente, Double custo) {
        double disponivel = cliente.getLimiteMensal() - cliente.getGastoMesAtual();
        if (disponivel < custo) {
            throw new SemSaldoLimiteException(
                    "Monthly limit exceeded. Available: R$" + String.format("%.2f", disponivel));
        }
        cliente.setGastoMesAtual(cliente.getGastoMesAtual() + custo);
        clienteRepository.save(cliente);
        return cliente.getGastoMesAtual();
    }

    public ClienteMudancaFinanceiroResponse recarregar(Long clienteId, Double valor) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Client not found"));

        if (cliente.getPlano() != PlanoEnum.PRE_PAGO) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Top-up available only for prepaid clients");
        }

        if (valor <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Amount must be greater than zero");
        }

        cliente.setSaldo(cliente.getSaldo() + valor);
        clienteRepository.save(cliente);

        return ClienteMudancaFinanceiroResponse.from(cliente);
    }

    public ClienteMudancaFinanceiroResponse atualizarLimite(Long clienteId, Double novoLimite) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Client not found"));

        if (cliente.getPlano() != PlanoEnum.POS_PAGO) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Limit update available only for postpaid clients");
        }

        if (novoLimite <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Limit must be greater than zero");
        }

        cliente.setLimiteMensal(novoLimite);
        clienteRepository.save(cliente);

        return ClienteMudancaFinanceiroResponse.from(cliente);
    }
}