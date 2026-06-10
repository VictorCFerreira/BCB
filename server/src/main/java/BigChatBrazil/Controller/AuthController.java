package BigChatBrazil.Controller;

import BigChatBrazil.domain.DTO.Request.AuthRequest;
import BigChatBrazil.domain.DTO.Request.CadastroRequest;
import BigChatBrazil.domain.DTO.Response.AuthResponse;
import BigChatBrazil.service.AuthService;
import BigChatBrazil.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth") @RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ClienteService clienteService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        return ResponseEntity.ok(authService.authenticate(req.documento()));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody CadastroRequest req) {
        clienteService.cadastrar(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
