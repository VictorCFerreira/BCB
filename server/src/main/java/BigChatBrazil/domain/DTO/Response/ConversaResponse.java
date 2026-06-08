package BigChatBrazil.domain.DTO.Response;

import BigChatBrazil.domain.Conversa;

import java.time.LocalDateTime;

public record ConversaResponse(
        Long id,
        String nomeDestinatario,
        String documentoDestinatario,
        LocalDateTime criadaEm
) {
    public static ConversaResponse from(Conversa c) {
        return new ConversaResponse(
                c.getId(),
                c.getNomeDestinatario(),
                c.getDocumentoDestinatario(),
                c.getCriadaEm()
        );
    }
}
