package in.myjavablog.notification;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class NotificationService {

    private NotificationRepository notificationRepository;

    public void send(NotificationRequest notificationRequest) {

        Notification notification = Notification.builder().sent_from(notificationRequest.getSent_from())
                .message(notificationRequest.getMessage()).sent_at(notificationRequest.getSent_at())
                .sent_to_email(notificationRequest.getSent_to_email()).sent_to_customerid(notificationRequest.getSent_to_customerid())
                .build();

        notificationRepository.save(notification);

        log.debug("Notification is triggered to customer {} successfully at {}", notificationRequest.getSent_to_customerid(),notificationRequest.getSent_at());
    }
}
