package BigChatBrazil.service;

import BigChatBrazil.domain.DTO.Response.ConversaResponse;
import BigChatBrazil.repository.ConversaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversaService {

    private final ConversaRepository conversaRepository;

    public List<ConversaResponse> listarPorCliente(Long clienteId) {
        return conversaRepository
                .findByClienteIdOrderByCriadaEmDesc(clienteId)
                .stream()
                .map(ConversaResponse::from)
                .toList();
    }
}
