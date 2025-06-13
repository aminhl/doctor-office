package org.nexthope.doctoroffice.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    NotificationRecord createNotification(NotificationRecord notificationRecord);

    void deleteNotification(Long notificationId);

    Page<NotificationRecord> getAllNotifications(Pageable pageable);

    NotificationRecord getNotification(Long notificationId);

}
