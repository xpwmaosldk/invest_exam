package example.kp.demo.domain.investment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InvestmentException extends Exception{
    final static int SOLD_OUT = 999;

    String message; 
    int code;

    public InvestmentException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
