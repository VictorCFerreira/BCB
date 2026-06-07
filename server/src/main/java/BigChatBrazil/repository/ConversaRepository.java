package BigChatBrazil.repository;

import BigChatBrazil.domain.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ConversaRepository extends JpaRepository<Conversa, Long> {
}
