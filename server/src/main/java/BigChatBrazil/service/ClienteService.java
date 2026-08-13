package BigChatBrazil.service;

import BigChatBrazil.Enum.PlanoEnum;
import BigChatBrazil.domain.Cliente;
import BigChatBrazil.domain.DTO.Request.CadastroRequest;
import BigChatBrazil.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public void cadastrar(CadastroRequest req) {
        if (clienteRepository.findByDocumento(req.documento()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Document already registered");
        }

        Cliente cliente = Cliente.builder()
                .nome(req.nome())
                .documento(req.documento())
                .plano(req.plano())
                .ativo(true)
                .saldo(req.plano() == PlanoEnum.PRE_PAGO ? 0.0 : null)
                .limiteMensal(req.plano() == PlanoEnum.POS_PAGO ? req.limiteMensal() : null)
                .gastoMesAtual(req.plano() == PlanoEnum.POS_PAGO ? 0.0 : null)
                .build();

        clienteRepository.save(cliente);
    }
}
