package example.kp.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import example.kp.demo.domain.investment.Investment;
import example.kp.demo.domain.investment.InvestmentRequestDto;
import example.kp.demo.domain.product.Product;
import example.kp.demo.domain.product.ProductRepository;
import example.kp.demo.domain.product.ProductState;
import example.kp.demo.domain.user.KpUser;
import example.kp.demo.domain.user.KpUserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InvestmentTest {
    @Autowired
    private KpUserRepository kpUserRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setup(){
        LocalDateTime now = LocalDateTime.now();
        List<KpUser> users = new ArrayList<>(); 
        List<Product> products = new ArrayList<>(); 
        for(int i = 1; i <= 100; i ++){
            users.add(KpUser.builder().id(i).build());
            products.add(
                Product.builder()
                .id(i)
                .totalInvestingAmount(50)
                .startedAt(now.minus(Period.ofDays(1)))
                .finishedAt(now.plus(Period.ofDays(1)))
                .title("t"+i)
                .state(ProductState.INVESTING)
                .build()
            );
        }
        kpUserRepository.saveAll(users);
        productRepository.saveAll(products);
        
    }

    @Test
    void investTest() throws InterruptedException{
        
        URI targetUrl = URI.create("/investments");
        int numberOfThreads = 100;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        InvestmentRequestDto order = new InvestmentRequestDto();
        order.setProductId(1);
        order.setAmount(1);
      
        for (int i = 1; i <= numberOfThreads; i++) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-USER-ID", i+"");
            HttpEntity<InvestmentRequestDto> entity = new HttpEntity<>(order, headers);
            
            service.execute(() -> {
                ResponseEntity<String> responseEntity = 
                    restTemplate.postForEntity(targetUrl, entity, String.class);
                latch.countDown();
            });
        }

        latch.await();
        
        assertEquals( productRepository.findById(1).getInvestedPeopleCnt(), 50);
    } 

    @Test
    void investmentOfUser(){

        URI targetUrl = URI.create("/investments");
        InvestmentRequestDto order = new InvestmentRequestDto();
        order.setAmount(1);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-USER-ID", 1+"");
        
        int investedPeople = 10;
        
        for (int i = 1; i <= investedPeople; i++) {  
            order.setProductId(i);
            HttpEntity<InvestmentRequestDto> postEntity = new HttpEntity<>(order, headers);
            ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(targetUrl, postEntity, String.class);
          
        }

        HttpEntity<Map<String, Object>> getEntity = new HttpEntity<>(null, headers);
        
        ResponseEntity<Investment[]> response = restTemplate.exchange(targetUrl, HttpMethod.GET, getEntity, Investment[].class);
        assertEquals(response.getBody().length, investedPeople);
    


    } 
}
