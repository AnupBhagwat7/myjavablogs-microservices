package in.myjavablog.notification;

import in.myjavablog.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class NotificationService {

    private NotificationRepository notificationRepository;

    public void send(NotificationRequest notificationRequest) {

        Notification notification = Notification.builder().sent_from(notificationRequest.getSent_from())
                .message(notificationRequest.getMessage()).sent_at(notificationRequest.getSent_at())
                .sent_to_email(notificationRequest.getSent_to_email()).sent_to_customerid(notificationRequest.getSent_to_customerid().toString())
                .build();

        notificationRepository.save(notification);

        log.debug("Notification is triggered to customer {} successfully at {}", notificationRequest.getSent_to_customerid(),notificationRequest.getSent_at());
    }

    @KafkaListener(topics = "notifications_topic", groupId = "consumer_notification_group", containerFactory = "notificationListener")
    public void sendFromTopic(NotificationRequest notificationRequest){

        log.debug("Notification received from topic "+ notificationRequest);

        Notification notification = Notification.builder().sent_from(notificationRequest.getSent_from())
                .message(notificationRequest.getMessage()).sent_at(notificationRequest.getSent_at())
                .sent_to_email(notificationRequest.getSent_to_email()).sent_to_customerid(notificationRequest.getSent_to_customerid().toString())
                .build();

        notificationRepository.save(notification);

        log.debug("Notification has been processed successfully fro customer : "+ notification.getSent_to_customerid());

    }
}
