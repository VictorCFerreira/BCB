package BigChatBrazil.domain.DTO.Request;


import BigChatBrazil.Enum.PrioridadeEnum;

public record EnviarMensagemRequest(
        Long conversaId,
        String documentoDestinatario,
        String conteudo,
        PrioridadeEnum prioridade
) {}
