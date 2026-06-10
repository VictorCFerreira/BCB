package BigChatBrazil.domain.DTO.Request;

import BigChatBrazil.Enum.PlanoEnum;

public record CadastroRequest(
        String nome,
        String documento,
        PlanoEnum plano,
        Double limiteMensal
) {}
