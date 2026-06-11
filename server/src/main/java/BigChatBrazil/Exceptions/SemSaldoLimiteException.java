package BigChatBrazil.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
public class SemSaldoLimiteException extends RuntimeException {
    public SemSaldoLimiteException(String mensagem) {
        super(mensagem);
    }
}
