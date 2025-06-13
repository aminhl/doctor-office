package org.nexthope.doctoroffice.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
// TODO: this is generic implementation that may need tweaks after
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public NotificationRecord createNotification(NotificationRecord notificationRecord) {
        log.debug("createNotification - Start: Attempting to create notification [{subject: {}, content: {}, recipient: {}]",
                notificationRecord.subject(), notificationRecord.content(), notificationRecord.recipient());
        var notificationSaved = notificationRepository.save(notificationRecord.toNotification()).toRecord();
        log.info("createNotification - Success: Notification created");
        log.debug("createNotification - End");
        return notificationSaved;
    }

    @Override
    public void deleteNotification(Long notificationId) {
        log.debug("deleteNotification - Start: Attempting to delete notification with id [{}]", notificationId);
        boolean notificationExists = notificationRepository.existsById(notificationId);
        if (!notificationExists) {
            log.warn("deleteNotification - Not found: Notification with id [{}] does not exist", notificationId);
            throw new NotificationNotFoundException(format("Notification with id %s not found", notificationId), NOT_FOUND);
        }
        notificationRepository.deleteById(notificationId);
        log.info("deleteNotification - Success: Notification deleted with id [{}]", notificationId);
        log.debug("deleteNotification - End");
    }

    @Override
    public Page<NotificationRecord> getAllNotifications(Pageable pageable) {
        log.debug("getAllNotifications - Started: Fetching notification with page number [{}], page size [{}], sort [{}]",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<NotificationRecord> result = notificationRepository.findAll(pageable)
                .map(Notification::toRecord);
        log.info("getAllNotifications - Success: Retrieved [{}] notifications (page {} of {})",
                result.getNumberOfElements(), result.getNumber()+1, result.getTotalPages());
        log.debug("getAllNotifications - End");
        return result;
    }

    @Override
    public NotificationRecord getNotification(Long notificationId) {
        log.debug("getNotification - Start: Fetching notification with id [{}]", notificationId);
        NotificationRecord result = notificationRepository.findById(notificationId)
                        .map(Notification::toRecord)
                        .orElseThrow(() -> new NotificationNotFoundException(format("Notification with id %s not found", notificationId), NOT_FOUND));
        log.info("getNotification - Success: Retrieved notification with id [{}]", notificationId);
        log.debug("getNotification - End");
        return result;
    }

}
