package in.myjavablog;

import in.myjavablog.clients.fraud.FraudClient;
import in.myjavablog.clients.fraud.FraudDetectionResult;
import in.myjavablog.clients.notification.NotificationClient;
import in.myjavablog.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService {

    private CustomerRepository customerRepository;

    private FraudClient fraudClient;

    private NotificationClient notificationClient;

    private KafkaTemplate<String, NotificationRequest> kafkaTemplate;

    public Customer registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {

        Customer customer = Customer.builder().firstName(customerRegistrationRequest.getFirstName())
                .lastName(customerRegistrationRequest.getLastName()).email(customerRegistrationRequest.getEmail()).build();

        customerRepository.saveAndFlush(customer);

        //Check fraud
        FraudDetectionResult fraudDetectionResult = fraudClient.checkFraudCustomer(customer.getId());
        log.debug("Fraud check response for customer {} is {} ", customer.getFirstName(),fraudDetectionResult.isFraudster());

        //Send Notification
        NotificationRequest notificationRequest = NotificationRequest.builder().sent_to_customerid(customer.getId())
        .message("Welcome , "+ customer.getFirstName() + " to MyJavaBlog ").sent_from("MyJavaBlog")
        .sent_to_email(customer.getEmail()).sent_at(LocalDateTime.now()).build();

        // notificationClient.sendNotification(notificationRequest);
        kafkaTemplate.send("notifications_topic", notificationRequest);

        log.debug("Notification sent to customer {} at {} ", customer.getFirstName(),notificationRequest.getSent_at());

        return customer;
    }
}
