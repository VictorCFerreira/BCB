package BigChatBrazil.repository;

import BigChatBrazil.domain.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ConversaRepository extends JpaRepository<Conversa, Long> {

    @Query("SELECT c FROM Conversa c WHERE c.clienteA.id = :clienteId OR c.clienteB.id = :clienteId")
    List<Conversa> findByParticipante(Long clienteId);

    @Query("SELECT c FROM Conversa c WHERE (c.clienteA.id = :aId AND c.clienteB.id = :bId) OR (c.clienteA.id = :bId AND c.clienteB.id = :aId)")
    Optional<Conversa> findByParticipantes(Long aId, Long bId);
}
