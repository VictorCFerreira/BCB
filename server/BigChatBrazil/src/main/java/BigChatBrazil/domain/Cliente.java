package BigChatBrazil.domain;

import BigChatBrazil.Enum.PlanoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "clientes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String documento;

    private String nome;

    private boolean ativo = true;

    @Enumerated(EnumType.STRING)
    private PlanoEnum plano;

    private Double saldo = 0.00;

    private Double limiteMensal;

    private Double gastoMesAtual = 0.00;


}