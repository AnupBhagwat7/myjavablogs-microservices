package in.myjavablog;

import in.myjavablog.clients.fraud.FraudDetectionResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fraud")
@Slf4j
@AllArgsConstructor
public class FraudDetectionController {

    private FraudDetectionService fraudDetectionService;

    @GetMapping("{custId}")
    public FraudDetectionResult chedkFraudCustomer(@PathVariable("custId") Long custId){

        return new FraudDetectionResult(fraudDetectionService.checkFraudCustomer(custId));

    }
}
