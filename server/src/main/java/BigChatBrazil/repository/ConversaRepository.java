package BigChatBrazil.repository;

import BigChatBrazil.domain.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ConversaRepository extends JpaRepository<Conversa, Long> {
    List<Conversa> findByClienteIdOrderByCriadaEmDesc(Long clienteId);
    Optional<Conversa> findByClienteIdAndDocumentoDestinatario(Long clienteId, String documentoDestinatario);
}
