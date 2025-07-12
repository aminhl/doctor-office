package org.nexthope.doctoroffice.notification;

import org.nexthope.doctoroffice.commons.PaginationRequest;
import org.nexthope.doctoroffice.commons.PagingResult;

public interface NotificationService {

    NotificationRecord createNotification(NotificationRecord notificationRecord);

    void deleteNotification(Long notificationId);

    PagingResult<NotificationRecord> findAllNotifications(PaginationRequest paginationRequest);

    NotificationRecord findNotification(Long notificationId);

}
