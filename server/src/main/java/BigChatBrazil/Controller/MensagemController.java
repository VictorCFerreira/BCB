package BigChatBrazil.Controller;

import BigChatBrazil.domain.Cliente;
import BigChatBrazil.domain.DTO.Request.EnviarMensagemRequest;
import BigChatBrazil.domain.DTO.Response.MensagemResponse;
import BigChatBrazil.service.MensagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensagens")
@RequiredArgsConstructor
@Tag(name = "Messages", description = "Message sending and retrieval")
public class MensagemController {

    private final MensagemService mensagemService;

    @Operation(summary = "Send message",
            description = "Sends a message to an existing conversation or creates a new one. " +
                    "Deducts the cost from balance (prepaid) or monthly limit (postpaid)")
    @ApiResponse(responseCode = "201", description = "Message queued successfully")
    @ApiResponse(responseCode = "402", description = "Insufficient balance or limit")
    @ApiResponse(responseCode = "404", description = "Conversation or recipient not found")
    @PostMapping
    public ResponseEntity<MensagemResponse> enviar(
            @RequestBody EnviarMensagemRequest req,
            @AuthenticationPrincipal Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mensagemService.enviar(cliente.getId(), req));
    }

    @Operation(summary = "List messages in a conversation",
            description = "Returns all messages in a conversation ordered by creation date")
    @ApiResponse(responseCode = "200", description = "Messages returned successfully")
    @ApiResponse(responseCode = "401", description = "Not authenticated")
    @GetMapping("/conversa/{conversaId}")
    public ResponseEntity<List<MensagemResponse>> listar(
            @Parameter(description = "Conversation ID") @PathVariable Long conversaId) {
        return ResponseEntity.ok(
                mensagemService.listarPorConversa(conversaId));
    }
}
