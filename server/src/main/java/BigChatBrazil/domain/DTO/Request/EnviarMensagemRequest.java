package BigChatBrazil.domain.DTO.Request;


public record EnviarMensagemRequest(
        Long conversaId,
        String documentoDestinatario,
        String nomeDestinatario,
        String conteudo
) {}
