package BigChatBrazil.Controller;

import BigChatBrazil.domain.Cliente;
import BigChatBrazil.domain.DTO.Response.ConversaResponse;
import BigChatBrazil.service.ConversaService;
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
public class ConversaController {

    private final ConversaService conversaService;

    @GetMapping
    public ResponseEntity<List<ConversaResponse>> listar(
            @AuthenticationPrincipal Cliente cliente) {
        return ResponseEntity.ok(
                conversaService.listarPorCliente(cliente.getId()));
    }
}
