package BigChatBrazil.service;

import BigChatBrazil.Enum.PlanoEnum;
import BigChatBrazil.domain.Cliente;
import BigChatBrazil.domain.DTO.Response.AuthResponse;
import BigChatBrazil.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final JwtService jwtService;

    public AuthResponse authenticate(String documento) {
        Cliente client = clienteRepository.findByDocumento(documento)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Client not found: " + documento));

        if (!client.isAtivo()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Inactive client");
        }

        String token = jwtService.generateToken(client);

        boolean isPrePago = client.getPlano() == PlanoEnum.PRE_PAGO;

        return new AuthResponse(
                token,
                client.getId(),
                client.getNome(),
                client.getPlano().name(),
                isPrePago ? client.getSaldo() : null,
                isPrePago ? null : client.getLimiteMensal(),
                isPrePago ? null : client.getGastoMesAtual()
        );
    }
}