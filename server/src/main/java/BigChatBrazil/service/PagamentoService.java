package BigChatBrazil.service;

import BigChatBrazil.Enum.PlanoEnum;
import BigChatBrazil.domain.Cliente;
import BigChatBrazil.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class PagamentoService {

    private static final Double CUSTO_MENSAGEM = 0.25;
    private final ClienteRepository clienteRepository;

    public Double cobrar(Cliente cliente) {
        if (cliente.getPlano() == PlanoEnum.PRE_PAGO) {
            return cobrarPrePago(cliente);
        } else {
            return cobrarPosPago(cliente);
        }
    }

    private Double cobrarPrePago(Cliente cliente) {
        if (cliente.getSaldo() < CUSTO_MENSAGEM) {
            throw new ResponseStatusException(
                    HttpStatus.PAYMENT_REQUIRED,
                    "Saldo insuficiente. Saldo atual: R$" + cliente.getSaldo()
            );
        }
        cliente.setSaldo(cliente.getSaldo() - CUSTO_MENSAGEM);
        clienteRepository.save(cliente);
        return cliente.getSaldo();
    }

    private Double cobrarPosPago(Cliente cliente) {
        double disponivel = cliente.getLimiteMensal() - cliente.getGastoMesAtual();
        if (disponivel < CUSTO_MENSAGEM) {
            throw new ResponseStatusException(
                    HttpStatus.PAYMENT_REQUIRED,
                    "Limite mensal excedido. Disponível: R$" + disponivel
            );
        }
        cliente.setGastoMesAtual(cliente.getGastoMesAtual() + CUSTO_MENSAGEM);
        clienteRepository.save(cliente);
        return cliente.getGastoMesAtual();
    }
}