package BigChatBrazil.infra.messages;

import BigChatBrazil.Enum.PrioridadeEnum;

import java.util.Optional;

public interface MessageQueue {
    void enqueue(Long mensagemId, PrioridadeEnum prioridade);
    Optional<Long> poll();
}
