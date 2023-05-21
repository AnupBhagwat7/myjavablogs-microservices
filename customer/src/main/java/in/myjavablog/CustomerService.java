package in.myjavablog;

import in.myjavablog.clients.fraud.FraudClient;
import in.myjavablog.clients.fraud.FraudDetectionResult;
import in.myjavablog.clients.notification.NotificationClient;
import in.myjavablog.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService {

    private CustomerRepository customerRepository;

    private RestTemplate restTemplate;

    private FraudClient fraudClient;

    private NotificationClient notificationClient;

    public Customer registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {

        Customer customer = Customer.builder().firstName(customerRegistrationRequest.getFirstName())
                .lastName(customerRegistrationRequest.getLastName()).email(customerRegistrationRequest.getEmail()).build();

        customerRepository.saveAndFlush(customer);

        //FraudDetectionResult fraudDetectionResult = restTemplate.getForObject("http://FRAUD/api/v1/fraud/{custId}", FraudDetectionResult.class, customer.getId());

        //Check fraud
        FraudDetectionResult fraudDetectionResult = fraudClient.chedkFraudCustomer(customer.getId());
        log.debug("Fraud check response for customer {} is {} ", customer.getFirstName(),fraudDetectionResult.isFraudster());

        //Send Notification
        NotificationRequest notificationRequest = NotificationRequest.builder().sent_to_customerid(customer.getId())
        .message("Welcome , "+ customer.getFirstName() + " to MyJavaBlog ").sent_from("MyJavaBlog")
        .sent_to_email(customer.getEmail()).sent_at(LocalDateTime.now()).build();

        notificationClient.sendNotification(notificationRequest);
        log.debug("Notification sent to customer {} at {} ", customer.getFirstName(),notificationRequest.getSent_at());

        return customer;
    }
}
