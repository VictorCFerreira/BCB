package BigChatBrazil.domain.DTO.Response;

import BigChatBrazil.Enum.StatusMensagemEnum;
import BigChatBrazil.domain.Mensagem;

import java.time.LocalDateTime;


public record MensagemResponse(
        Long id,
        String conteudo,
        StatusMensagemEnum status,
        Double custo,
        LocalDateTime criadaEm
) {
    public static MensagemResponse from(Mensagem m) {
        return new MensagemResponse(
                m.getId(),
                m.getConteudo(),
                m.getStatus(),
                m.getCusto(),
                m.getCriadaEm()
        );
    }
}
