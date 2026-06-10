package BigChatBrazil.infra.messages;

import BigChatBrazil.Enum.StatusMensagemEnum;
import BigChatBrazil.domain.DTO.Response.MensagemResponse;
import BigChatBrazil.repository.MensagemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageWorker {

    private final MessageQueue messageQueue;
    private final MensagemRepository mensagemRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedDelay = 2000)
    public void processar() {
        Optional<Long> next;
        while ((next = messageQueue.poll()).isPresent()) {
            Long id = next.get();
            mensagemRepository.findById(id).ifPresent(msg -> {
                msg.setStatus(StatusMensagemEnum.PROCESSANDO);
                mensagemRepository.save(msg);

                try {
                    Thread.sleep(200);
                    msg.setStatus(StatusMensagemEnum.ENVIADA);
                    msg.setProcessadaEm(LocalDateTime.now());
                } catch (Exception e) {
                    msg.setStatus(StatusMensagemEnum.FALHA);
                    log.error("Falha ao processar mensagem {}", id, e);
                }

                mensagemRepository.save(msg);

                // publica no tópico da conversa
                messagingTemplate.convertAndSend(
                        "/topic/conversa/" + msg.getConversa().getId(),
                        MensagemResponse.from(msg, null)
                );
            });
        }
    }
}
