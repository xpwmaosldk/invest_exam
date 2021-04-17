package example.kp.demo.domain.investment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class InvestmentController {
    private InvestmentService investmentService;

    @PostMapping("/investments")
    public ResponseEntity<?> invest(@RequestHeader("X-USER-ID") long userId, @RequestBody InvestmentRequestDto dto) throws InvestmentException {
        return ResponseEntity.ok(investmentService.save(userId, dto));
    }

    @GetMapping("/investments")
    public ResponseEntity<?> findInvest(@RequestHeader("X-USER-ID") long userId) {
        return ResponseEntity.ok(investmentService.findByUser(userId));
    }
}
