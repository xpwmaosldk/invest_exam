package example.kp.demo.domain.product;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import example.kp.demo.domain.user.KpUser;
import example.kp.demo.domain.user.KpUserRepository;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ProductController {
    private ProductRepository productRepository;
    private KpUserRepository kpUserRepository;
    private ProductService productService;

    @GetMapping("/test")
    public ResponseEntity<?> test(@RequestParam ProductState state) {
        kpUserRepository.save(KpUser.builder().build());
        Product p = Product.builder().totalInvestingAmount(3).build();
        p.startedAt = LocalDateTime.of(2021, 1, 1, 12, 0, 0);
        p.finishedAt =  LocalDateTime.of(2021, 11, 1, 12, 0, 0);
        p.state = state;
        return ResponseEntity.ok( productRepository.save(p));
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts(){
        return ResponseEntity.ok(productService.findAll());
    }
}
