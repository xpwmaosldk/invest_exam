package example.kp.demo.domain.investment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvestmentAdvice {
    @ExceptionHandler(InvestmentException.class)
    public ResponseEntity<String> handleConflict(InvestmentException ex) {
        return ResponseEntity.status(ex.getCode()).body(ex.getMessage());
    }
}
