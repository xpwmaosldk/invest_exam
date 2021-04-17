package example.kp.demo.domain.investment;

import lombok.Data;

@Data
public class InvestmentRequestDto {
    long productId;
    long amount;
}
