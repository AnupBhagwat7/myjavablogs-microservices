package in.myjavablog.notification;

import in.myjavablog.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
@Slf4j
@AllArgsConstructor
public class NotificationController {

    private NotificationService notificationService;

    @PostMapping("/send")
    public void sendNotification(@RequestBody NotificationRequest notificationRequest) {

        notificationService.send(notificationRequest);

        log.debug("Notification is triggered to customer {} successfully at {}", notificationRequest.getSent_to_customerid(), notificationRequest.getSent_at());
    }
}
