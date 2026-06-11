package BigChatBrazil.domain.DTO.Response;

import BigChatBrazil.domain.Cliente;

public record ClienteMudancaFinanceiroResponse(
        Long id,
        String nome,
        String plano,
        Double saldo,
        Double limiteMensal,
        Double gastoMesAtual
) {
    public static ClienteMudancaFinanceiroResponse from(Cliente c) {
        return new ClienteMudancaFinanceiroResponse(
                c.getId(),
                c.getNome(),
                c.getPlano().name(),
                c.getSaldo(),
                c.getLimiteMensal(),
                c.getGastoMesAtual()
        );
    }
}
