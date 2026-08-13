package BigChatBrazil.Controller;

import BigChatBrazil.domain.Cliente;
import BigChatBrazil.domain.DTO.Response.ConversaResponse;
import BigChatBrazil.service.ConversaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/conversas")
@RequiredArgsConstructor
@Tag(name = "Conversations", description = "Conversation listing for the authenticated client")
public class ConversaController {

    private final ConversaService conversaService;

    @Operation(summary = "List conversations", description = "Returns all conversations where the authenticated client is a participant")
    @ApiResponse(responseCode = "200", description = "List returned successfully")
    @ApiResponse(responseCode = "401", description = "Not authenticated")
    @GetMapping
    public ResponseEntity<List<ConversaResponse>> listar(
            @AuthenticationPrincipal Cliente cliente) {
        return ResponseEntity.ok(
                conversaService.listarPorCliente(cliente.getId()));
    }
}
