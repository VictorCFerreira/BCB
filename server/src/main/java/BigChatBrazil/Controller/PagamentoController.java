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
@Tag(name = "Pagamento", description = "Gestão financeira do cliente autenticado")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @Operation(summary = "Recarregar saldo",
            description = "Adiciona créditos ao saldo do cliente pré-pago")
    @ApiResponse(responseCode = "200", description = "Recarga realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Operação inválida para o tipo de plano ou valor inválido")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @PostMapping("/recarregar")
    public ResponseEntity<ClienteMudancaFinanceiroResponse> recarregar(
            @RequestBody RecargaRequest req,
            @AuthenticationPrincipal Cliente cliente) {
        return ResponseEntity.ok(
                pagamentoService.recarregar(cliente.getId(), req.valor()));
    }

    @Operation(summary = "Atualizar limite mensal",
            description = "Atualiza o limite mensal de consumo do cliente pós-pago")
    @ApiResponse(responseCode = "200", description = "Limite atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Operação inválida para o tipo de plano ou valor inválido")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @PutMapping("/limite")
    public ResponseEntity<ClienteMudancaFinanceiroResponse> atualizarLimite(
            @RequestBody AtualizarLimiteRequest req,
            @AuthenticationPrincipal Cliente cliente) {
        return ResponseEntity.ok(
                pagamentoService.atualizarLimite(cliente.getId(), req.novoLimite()));
    }
}