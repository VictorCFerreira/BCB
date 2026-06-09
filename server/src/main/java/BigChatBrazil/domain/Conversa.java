package BigChatBrazil.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversas")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Conversa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_a_id", nullable = false)
    private Cliente clienteA;   // quem iniciou

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_b_id", nullable = false)
    private Cliente clienteB;   // destinatário

    private LocalDateTime criadaEm = LocalDateTime.now();
}