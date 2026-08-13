package BigChatBrazil.Controller;

import BigChatBrazil.domain.DTO.Request.AuthRequest;
import BigChatBrazil.domain.DTO.Request.CadastroRequest;
import BigChatBrazil.domain.DTO.Response.AuthResponse;
import BigChatBrazil.service.AuthService;
import BigChatBrazil.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication and client registration endpoints")
public class AuthController {

    private final AuthService authService;
    private final ClienteService clienteService;

    @Operation(summary = "Authenticate client", description = "Authenticates by CPF or CNPJ and returns a JWT token")
    @ApiResponse(responseCode = "200", description = "Authenticated successfully")
    @ApiResponse(responseCode = "403", description = "Inactive client")
    @ApiResponse(responseCode = "404", description = "Client not found")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        return ResponseEntity.ok(authService.authenticate(req.documento()));
    }

    @Operation(summary = "Register client", description = "Creates a new prepaid or postpaid client")
    @ApiResponse(responseCode = "201", description = "Client registered successfully")
    @ApiResponse(responseCode = "409", description = "Document already registered")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody CadastroRequest req) {
        clienteService.cadastrar(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
