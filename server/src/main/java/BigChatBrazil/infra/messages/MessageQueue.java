package BigChatBrazil.infra.messages;

import java.util.Optional;

public interface MessageQueue {
    void enqueue(Long mensagemId);
    Optional<Long> poll();
}
