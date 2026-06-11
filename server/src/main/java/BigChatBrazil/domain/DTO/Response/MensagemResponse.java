package BigChatBrazil.domain.DTO.Response;

import BigChatBrazil.Enum.PrioridadeEnum;
import BigChatBrazil.Enum.StatusMensagemEnum;
import BigChatBrazil.domain.Mensagem;

import java.time.LocalDateTime;


public record MensagemResponse(
        Long id,
        String conteudo,
        PrioridadeEnum prioridade,
        StatusMensagemEnum status,
        Double custo,
        LocalDateTime criadaEm,
        Long remetenteId,
        Double valorAtualizado
) {
    public static MensagemResponse from(Mensagem m, Double valorAtualizado) {
        return new MensagemResponse(
                m.getId(),
                m.getConteudo(),
                m.getPrioridade(),
                m.getStatus(),
                m.getCusto(),
                m.getCriadaEm(),
                m.getCliente().getId(),
                valorAtualizado
        );
    }
}
