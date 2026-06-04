package BigChatBrazil.domain.DTO.Response;

import java.math.BigDecimal;
import java.util.UUID;

public record AuthResponse(String token, UUID clientId, String nome,
                           String planType, Double saldo) {}
