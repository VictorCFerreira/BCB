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
@Tag(name = "Autenticação", description = "Endpoints de autenticação e cadastro de clientes")
public class AuthController {

    private final AuthService authService;
    private final ClienteService clienteService;

    @Operation(summary = "Autenticar cliente", description = "Autentica pelo CPF ou CNPJ e retorna o token JWT")
    @ApiResponse(responseCode = "200", description = "Autenticado com sucesso")
    @ApiResponse(responseCode = "403", description = "Cliente inativo")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        return ResponseEntity.ok(authService.authenticate(req.documento()));
    }

    @Operation(summary = "Cadastrar cliente", description = "Cria um novo cliente pré ou pós-pago")
    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso")
    @ApiResponse(responseCode = "409", description = "Documento já cadastrado")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody CadastroRequest req) {
        clienteService.cadastrar(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
