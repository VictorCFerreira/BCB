package BigChatBrazil.repository;

import BigChatBrazil.domain.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
}
