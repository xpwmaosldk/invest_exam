package example.kp.demo.domain.product;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface ProductRepository extends JpaRepository<Product, Long>{
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Product findById(long id);
    List<Product> findByState(ProductState state);
    List<Product> findAllByStartedAtBeforeAndFinishedAtAfter(LocalDateTime startedAt, LocalDateTime finishedAt);
}
