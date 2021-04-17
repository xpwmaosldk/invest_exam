package example.kp.demo.domain.product;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public List<Product> findAll(){
        LocalDateTime now = LocalDateTime.now();
        return productRepository.findAllByStartedAtBeforeAndFinishedAtAfter(now,now);
    }
}
