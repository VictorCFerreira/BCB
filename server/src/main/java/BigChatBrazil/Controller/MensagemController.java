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
@Tag(name = "Mensagens", description = "Envio e consulta de mensagens")
public class MensagemController {

    private final MensagemService mensagemService;

    @Operation(summary = "Enviar mensagem",
            description = "Envia uma mensagem para uma conversa existente ou cria uma nova conversa. " +
                    "Debita o custo do saldo (pré-pago) ou do limite mensal (pós-pago)")
    @ApiResponse(responseCode = "201", description = "Mensagem enfileirada com sucesso")
    @ApiResponse(responseCode = "402", description = "Saldo ou limite insuficiente")
    @ApiResponse(responseCode = "404", description = "Conversa ou destinatário não encontrado")
    @PostMapping
    public ResponseEntity<MensagemResponse> enviar(
            @RequestBody EnviarMensagemRequest req,
            @AuthenticationPrincipal Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mensagemService.enviar(cliente.getId(), req));
    }

    @Operation(summary = "Listar mensagens de uma conversa",
            description = "Retorna todas as mensagens de uma conversa ordenadas por data de criação")
    @ApiResponse(responseCode = "200", description = "Mensagens retornadas com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @GetMapping("/conversa/{conversaId}")
    public ResponseEntity<List<MensagemResponse>> listar(
            @Parameter(description = "ID da conversa") @PathVariable Long conversaId) {
        return ResponseEntity.ok(
                mensagemService.listarPorConversa(conversaId));
    }
}
