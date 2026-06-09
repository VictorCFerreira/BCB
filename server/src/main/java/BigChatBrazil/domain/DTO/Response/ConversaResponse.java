package BigChatBrazil.domain.DTO.Response;

import BigChatBrazil.domain.Cliente;
import BigChatBrazil.domain.Conversa;

import java.time.LocalDateTime;

public record ConversaResponse(
        Long id,
        String nomeOutroParticipante,
        String documentoOutroParticipante,
        LocalDateTime criadaEm
) {
    public static ConversaResponse from(Conversa c, Long clienteLogadoId) {
        boolean logadoEhA = c.getClienteA().getId().equals(clienteLogadoId);
        Cliente outro = logadoEhA ? c.getClienteB() : c.getClienteA();

        return new ConversaResponse(
                c.getId(),
                outro.getNome(),
                outro.getDocumento(),
                c.getCriadaEm()
        );
    }
}