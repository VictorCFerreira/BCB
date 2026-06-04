package BigChatBrazil.Enum;

public enum PlanoEnum {

    PRE_PAGO("Pré-pago"),
    POS_PAGO("Pós-pago");

    private final String descricao;

    PlanoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
