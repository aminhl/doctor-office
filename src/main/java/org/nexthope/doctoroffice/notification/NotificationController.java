package org.nexthope.doctoroffice.notification;

import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.time.Instant.now;
import static org.nexthope.doctoroffice.commons.DoctorOfficeConstants.API_V1_PATH;
import static org.nexthope.doctoroffice.notification.NotificationConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(API_V1_PATH + NOTIFICATION_ENDPOINT)
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<NotificationRecord>> createNotification(@RequestBody @Validated NotificationRecord notificationRecord) {
        var notificationSaved = notificationService.createNotification(notificationRecord);
        ApiResponse<NotificationRecord> apiResponse = ApiResponse.<NotificationRecord>builder()
                .success(true)
                .message(NOTIFICATION_CREATED_MESSAGE)
                .data(notificationSaved)
                .statusCode(CREATED)
                .timestamp(now())
                .build();
        return ResponseEntity.status(CREATED).body(apiResponse);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<Object>> deleteNotification(@PathVariable("notificationId") Long notificationId) {
        notificationService.deleteNotification(notificationId);
        ApiResponse<Object> apiResponse = ApiResponse.<Object>builder()
                .success(true)
                .message(NOTIFICATION_DELETED_MESSAGE)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<NotificationRecord>>> getAllNotifications(Pageable pageable) {
        Page<NotificationRecord> page = notificationService.getAllNotifications(pageable);
        ApiResponse<Page<NotificationRecord>> response = ApiResponse.<Page<NotificationRecord>>builder()
                .success(true)
                .message(NOTIFICATIONS_RETRIEVED_MESSAGE)
                .statusCode(OK)
                .data(page)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<NotificationRecord>> getNotification(@PathVariable("notificationId") Long notificationId) {
        NotificationRecord notification = notificationService.getNotification(notificationId);
        ApiResponse<NotificationRecord> response = ApiResponse.<NotificationRecord>builder()
                .success(true)
                .message(NOTIFICATION_RETRIEVED_MESSAGE)
                .statusCode(OK)
                .data(notification)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(response);
    }

}
