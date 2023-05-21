package in.myjavablog.clients.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("fraud")
public interface FraudClient {

    @GetMapping("/api/v1/fraud/{custId}")
    FraudDetectionResult chedkFraudCustomer(@PathVariable("custId") Long custId);

}
