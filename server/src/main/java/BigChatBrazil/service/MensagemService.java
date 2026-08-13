package BigChatBrazil.service;

import BigChatBrazil.Enum.PrioridadeEnum;
import BigChatBrazil.Enum.StatusMensagemEnum;
import BigChatBrazil.domain.Cliente;
import BigChatBrazil.domain.Conversa;
import BigChatBrazil.domain.DTO.Request.EnviarMensagemRequest;
import BigChatBrazil.domain.DTO.Response.MensagemResponse;
import BigChatBrazil.domain.Mensagem;
import BigChatBrazil.infra.messages.MessageQueue;
import BigChatBrazil.repository.ClienteRepository;
import BigChatBrazil.repository.ConversaRepository;
import BigChatBrazil.repository.MensagemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final ConversaRepository conversaRepository;
    private final ClienteRepository clienteRepository;
    private final PagamentoService pagamentoService;
    private final MessageQueue messageQueue;

    public MensagemResponse enviar(Long clienteId, EnviarMensagemRequest req) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Client not found"));

        Conversa conversa = resolverConversa(cliente, req);

        PrioridadeEnum prioridade = req.prioridade() != null
                ? req.prioridade()
                : PrioridadeEnum.NORMAL;

        Double valorAtualizado = pagamentoService.cobrar(cliente, prioridade);
        Double custo = pagamentoService.calcularCusto(prioridade);

        Mensagem mensagem = Mensagem.builder()
                .conversa(conversa)
                .cliente(cliente)
                .conteudo(req.conteudo())
                .prioridade(prioridade)
                .status(StatusMensagemEnum.ENFILEIRADA)
                .custo(custo)
                .build();

        mensagem = mensagemRepository.save(mensagem);
        messageQueue.enqueue(mensagem.getId(), prioridade);

        return MensagemResponse.from(mensagem, valorAtualizado);
    }

    public List<MensagemResponse> listarPorConversa(Long conversaId) {
        return mensagemRepository
                .findByConversaIdOrderByCriadaEmAsc(conversaId)
                .stream()
                .map(m -> MensagemResponse.from(m, null))
                .toList();
    }

    private Conversa resolverConversa(Cliente remetente, EnviarMensagemRequest req) {
        if (req.conversaId() != null) {
            return conversaRepository.findById(req.conversaId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Conversation not found"));
        }

        Cliente destinatario = clienteRepository
                .findByDocumento(req.documentoDestinatario())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recipient not found: " + req.documentoDestinatario()));

        return conversaRepository
                .findByParticipantes(remetente.getId(), destinatario.getId())
                .orElseGet(() -> conversaRepository.save(
                        Conversa.builder()
                                .clienteA(remetente)
                                .clienteB(destinatario)
                                .build()
                ));
    }
}

