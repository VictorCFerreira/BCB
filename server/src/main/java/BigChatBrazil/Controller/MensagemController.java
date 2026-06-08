package BigChatBrazil.Controller;

import BigChatBrazil.domain.Cliente;
import BigChatBrazil.domain.DTO.Request.EnviarMensagemRequest;
import BigChatBrazil.domain.DTO.Response.MensagemResponse;
import BigChatBrazil.service.MensagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensagens")
@RequiredArgsConstructor
public class MensagemController {

    private final MensagemService mensagemService;

    @PostMapping
    public ResponseEntity<MensagemResponse> enviar(
            @RequestBody EnviarMensagemRequest req,
            @AuthenticationPrincipal Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mensagemService.enviar(cliente.getId(), req));
    }

    @GetMapping("/conversa/{conversaId}")
    public ResponseEntity<List<MensagemResponse>> listar(
            @PathVariable Long conversaId) {
        return ResponseEntity.ok(
                mensagemService.listarPorConversa(conversaId));
    }
}
