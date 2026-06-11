package BigChatBrazil.infra.messages;

import BigChatBrazil.Enum.PrioridadeEnum;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.PriorityBlockingQueue;

@Component
@Primary
public class PriorityMessageQueue implements MessageQueue {

    private record QueuedItem(Long mensagemId, PrioridadeEnum prioridade, Instant enqueuedAt) {}

    private final PriorityBlockingQueue<QueuedItem> queue = new PriorityBlockingQueue<>(
            256,
            Comparator
                    .comparingInt((QueuedItem i) -> i.prioridade() == PrioridadeEnum.URGENTE ? 0 : 1)
                    .thenComparing(QueuedItem::enqueuedAt)
    );

    @Override
    public void enqueue(Long mensagemId, PrioridadeEnum prioridade) {
        queue.offer(new QueuedItem(mensagemId, prioridade, Instant.now()));
    }

    @Override
    public Optional<Long> poll() {
        return Optional.ofNullable(queue.poll()).map(QueuedItem::mensagemId);
    }
}
