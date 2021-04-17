package example.kp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import example.kp.demo.domain.product.Product;
import example.kp.demo.domain.product.ProductRepository;
import example.kp.demo.domain.product.ProductState;

// @ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductTest {
    @Autowired
    private ProductRepository productRepository;
    // @Autowired
    // private ProductService productService;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setup() {
        LocalDateTime now = LocalDateTime.now();
        List<Product> products = new ArrayList<>();
        products.add(
            Product.builder()
            .totalInvestingAmount(100)
            .startedAt(now.minus(Period.ofDays(1)))
            .finishedAt(now.plus(Period.ofDays(1)))
            .title("t1")
            .state(ProductState.INVESTING)
            .build()
        );

        products.add(
            Product.builder()
            .totalInvestingAmount(100)
            .startedAt(now.plus(Period.ofDays(1)))
            .finishedAt(now.minus(Period.ofDays(1)))
            .title("t2")
            .state(ProductState.INVESTING)
            .build()
        );

        productRepository.saveAll(products);
    }

    @Test
    void getProductList() throws JSONException{
        URI targetUrl = URI.create("/products");
        
        ResponseEntity<Product[]> responseEntity = restTemplate.getForEntity(targetUrl, Product[].class);
        System.out.println(responseEntity.getBody());
        
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody()[0].getTitle(), "t1");

    }

}
