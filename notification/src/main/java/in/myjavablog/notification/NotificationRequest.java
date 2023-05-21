package in.myjavablog.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NotificationRequest {

    private String sent_from;
    private String sent_to_email;
    private String sent_to_customerid;
    private String message;
    private LocalDateTime sent_at;
}
