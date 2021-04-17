package example.kp.demo.domain.investment;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import example.kp.demo.domain.product.Product;
import example.kp.demo.domain.product.ProductRepository;
import example.kp.demo.domain.product.ProductState;
import example.kp.demo.domain.user.KpUser;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvestmentService {
    private InvestmentRepository investRepository;
    private ProductRepository productRepository;

    @Transactional(rollbackOn = SoldOutException.class)
    public String save(long userId, InvestmentRequestDto dto) throws InvestmentException {

        Product product = productRepository.findById(dto.productId);

        if (product.getState() == ProductState.FINISHED) {
            throw new SoldOutException();
        }

        long sumAmount = product.getCurrentInvestingAmount() + dto.amount;

        if (sumAmount >= product.getTotalInvestingAmount()) {
            sumAmount = product.getTotalInvestingAmount();
            dto.amount = product.getTotalInvestingAmount() - product.getCurrentInvestingAmount();
            product.setState(ProductState.FINISHED);
        } 

        product.setCurrentInvestingAmount(sumAmount);   

        Investment investment = Investment.builder()
                .product(product)
                .user(KpUser.builder().id(userId).build())
                .investingAmount(dto.amount)
                .build();
        investRepository.save(investment);

        return "invested";

    }

    public List<Investment> findByUser(long userId) {
        return investRepository.findByUser_Id(userId);
    }
}
