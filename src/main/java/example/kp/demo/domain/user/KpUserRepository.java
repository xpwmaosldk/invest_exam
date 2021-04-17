package example.kp.demo.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KpUserRepository extends JpaRepository<KpUser, Long>{
    
}
