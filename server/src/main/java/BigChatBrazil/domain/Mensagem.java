package BigChatBrazil.domain;

import BigChatBrazil.Enum.StatusMensagemEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensagens")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversa_id", nullable = false)
    private Conversa conversa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private String conteudo;

    @Enumerated(EnumType.STRING)
    private StatusMensagemEnum status = StatusMensagemEnum.ENFILEIRADA;

    private Double custo;

    private LocalDateTime criadaEm = LocalDateTime.now();
    private LocalDateTime processadaEm;
}
