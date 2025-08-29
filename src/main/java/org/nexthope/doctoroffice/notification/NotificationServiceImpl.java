package org.nexthope.doctoroffice.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nexthope.doctoroffice.commons.pagination.PaginationRequest;
import org.nexthope.doctoroffice.commons.pagination.PaginationUtils;
import org.nexthope.doctoroffice.commons.pagination.PagingResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public NotificationDTO create(NotificationDTO notificationDTO) {
        log.debug("createNotification - Start: Attempting to create notification { subject:{}, content:{}, recipient:{} }",
                notificationDTO.subject(), notificationDTO.content(), notificationDTO.recipient());
        var notification = notificationRepository.save(notificationDTO.toNotification()).toRecord();
        log.info("createNotification - Success: Notification created");
        log.debug("createNotification - End");
        return notification;
    }

    @Override
    public void delete(Long notificationId) {
        log.debug("deleteNotification - Start: Attempting to delete notification with id:{}", notificationId);
        boolean notificationExists = notificationRepository.existsById(notificationId);
        if (!notificationExists) {
            log.warn("deleteNotification - Not found: Notification with id:{} does not exist", notificationId);
            throw new NotificationNotFoundException(format("Notification with id %s not found", notificationId), NOT_FOUND);
        }
        notificationRepository.deleteById(notificationId);
        log.info("deleteNotification - Success: Notification deleted with id:{}", notificationId);
        log.debug("deleteNotification - End");
    }

    @Override
    public PagingResult<NotificationDTO> findAll(PaginationRequest paginationRequest) {
        final Pageable pageable = PaginationUtils.getPageable(paginationRequest);
        log.debug("getAllNotifications - Started: Fetching notification with page_number:{}, page_size:{}, sort:{}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<NotificationDTO> notificationRecords = notificationRepository.findAll(pageable)
                .map(Notification::toRecord);
        log.info("getAllNotifications - Success: Retrieved:{} notification(s) (page:{} of:{})",
                notificationRecords.getNumberOfElements(), notificationRecords.getNumber()+1, notificationRecords.getTotalPages());
        log.debug("getAllNotifications - End");
        return new PagingResult<>(
                notificationRecords.stream().toList(),
                notificationRecords.getTotalPages(),
                notificationRecords.getTotalElements(),
                notificationRecords.getSize(),
                notificationRecords.getNumber(),
                notificationRecords.isEmpty()
        );
    }

    @Override
    public NotificationDTO find(Long notificationId) {
        log.debug("getNotification - Start: Fetching notification with id:{}", notificationId);
        NotificationDTO result = notificationRepository.findById(notificationId)
                        .map(Notification::toRecord)
                        .orElseThrow(() -> new NotificationNotFoundException(format("Notification with id %s not found", notificationId), NOT_FOUND));
        log.info("getNotification - Success: Retrieved notification with id:{}", notificationId);
        log.debug("getNotification - End");
        return result;
    }

}
