package BigChatBrazil.repository;

import BigChatBrazil.domain.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByConversaIdOrderByCriadaEmAsc(Long conversaId);

}
