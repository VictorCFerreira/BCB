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
@Tag(name = "Conversas", description = "Listagem de conversas do cliente autenticado")
public class ConversaController {

    private final ConversaService conversaService;

    @Operation(summary = "Listar conversas", description = "Retorna todas as conversas onde o cliente autenticado é participante")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @GetMapping
    public ResponseEntity<List<ConversaResponse>> listar(
            @AuthenticationPrincipal Cliente cliente) {
        return ResponseEntity.ok(
                conversaService.listarPorCliente(cliente.getId()));
    }
}
