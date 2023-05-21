package in.myjavablog;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class FraudDetectionService {

    private FraudDetectionHistoryRepository fraudDetectionHistoryRepository;
    public boolean checkFraudCustomer(Long custId) {

        boolean fraudster = false;

        //TODO - To check fraud customer

        FraudDetectionHistory fraudDetectionHistory = FraudDetectionHistory.builder().
                custId(custId).farudster(fraudster).createdAt(LocalDateTime.now()).build();

        fraudDetectionHistoryRepository.save(fraudDetectionHistory);

        return fraudster;
    }
}
