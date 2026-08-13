package BigChatBrazil.Controller;

import BigChatBrazil.domain.Cliente;
import BigChatBrazil.domain.DTO.Request.AtualizarLimiteRequest;
import BigChatBrazil.domain.DTO.Request.RecargaRequest;
import BigChatBrazil.domain.DTO.Response.ClienteMudancaFinanceiroResponse;
import BigChatBrazil.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamento")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Financial management for the authenticated client")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @Operation(summary = "Top up balance",
            description = "Adds credits to a prepaid client's balance")
    @ApiResponse(responseCode = "200", description = "Top-up completed successfully")
    @ApiResponse(responseCode = "400", description = "Invalid operation for plan type or invalid amount")
    @ApiResponse(responseCode = "404", description = "Client not found")
    @PostMapping("/recarregar")
    public ResponseEntity<ClienteMudancaFinanceiroResponse> recarregar(
            @RequestBody RecargaRequest req,
            @AuthenticationPrincipal Cliente cliente) {
        return ResponseEntity.ok(
                pagamentoService.recarregar(cliente.getId(), req.valor()));
    }

    @Operation(summary = "Update monthly limit",
            description = "Updates the monthly spending limit for a postpaid client")
    @ApiResponse(responseCode = "200", description = "Limit updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid operation for plan type or invalid amount")
    @ApiResponse(responseCode = "404", description = "Client not found")
    @PutMapping("/limite")
    public ResponseEntity<ClienteMudancaFinanceiroResponse> atualizarLimite(
            @RequestBody AtualizarLimiteRequest req,
            @AuthenticationPrincipal Cliente cliente) {
        return ResponseEntity.ok(
                pagamentoService.atualizarLimite(cliente.getId(), req.novoLimite()));
    }
}
