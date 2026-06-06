package BigChatBrazil.domain.DTO.Response;



public record AuthResponse(String token, Long clientId, String nome,
                           String planType, Double saldo) {}
