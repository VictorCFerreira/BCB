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
        return conversaRepository.findByParticipante(clienteId)
                .stream()
                .map(c -> ConversaResponse.from(c, clienteId))
                .toList();
    }
}
