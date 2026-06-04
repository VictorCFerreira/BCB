package BigChatBrazil.service;

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

    private final ClienteRepository clientRepository;
    private final JwtService jwtService;

    public AuthResponse authenticate(String documentId) {
        // No BCB não tem senha — o documentId é o "login"
        Cliente client = clientRepository.findByDocumento(documentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente não encontrado: " + documentId));

        if (!client.isAtivo()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cliente inativo");
        }

        String token = jwtService.generateToken(client);

        return new AuthResponse(
                token,
                client.getId(),
                client.getNome(),
                client.getPlano().name(),
                client.getSaldo()
        );
    }
}