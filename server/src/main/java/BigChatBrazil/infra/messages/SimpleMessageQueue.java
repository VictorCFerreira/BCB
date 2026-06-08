package BigChatBrazil.infra.messages;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class SimpleMessageQueue implements MessageQueue {

    private final Queue<Long> queue = new ConcurrentLinkedQueue<>();

    @Override
    public void enqueue(Long mensagemId) {
        queue.offer(mensagemId);
    }

    @Override
    public Optional<Long> poll() {
        return Optional.ofNullable(queue.poll());
    }
}
