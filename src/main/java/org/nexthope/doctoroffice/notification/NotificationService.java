package org.nexthope.doctoroffice.notification;

import org.nexthope.doctoroffice.commons.pagination.PaginationRequest;
import org.nexthope.doctoroffice.commons.pagination.PagingResult;

public interface NotificationService {

    NotificationDTO create(NotificationDTO notificationDTO);

    void delete(Long notificationId);

    PagingResult<NotificationDTO> findAll(PaginationRequest paginationRequest);

    NotificationDTO find(Long notificationId);

}
