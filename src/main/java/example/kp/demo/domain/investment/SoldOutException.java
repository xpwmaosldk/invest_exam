package example.kp.demo.domain.investment;

public class SoldOutException extends InvestmentException{

    public SoldOutException() {
        super("sold-out", InvestmentException.SOLD_OUT);
    }

}
