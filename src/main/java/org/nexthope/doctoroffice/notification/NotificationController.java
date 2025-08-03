package org.nexthope.doctoroffice.notification;

import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResult;
import org.nexthope.doctoroffice.commons.PaginationRequest;
import org.nexthope.doctoroffice.commons.PagingResult;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.time.Instant.now;
import static org.nexthope.doctoroffice.notification.NotificationConstants.NOTIFICATION_ENDPOINT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(NOTIFICATION_ENDPOINT)
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResult<NotificationRecord>> createNotification(@RequestBody @Validated NotificationRecord notificationRecord) {
        var notificationSaved = notificationService.createNotification(notificationRecord);
        ApiResult<NotificationRecord> apiResult = ApiResult.<NotificationRecord>builder()
                .success(true)
                .data(notificationSaved)
                .statusCode(CREATED)
                .timestamp(now())
                .build();
        return ResponseEntity.status(CREATED).body(apiResult);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ApiResult<Object>> deleteNotification(@PathVariable("notificationId") Long notificationId) {
        notificationService.deleteNotification(notificationId);
        ApiResult<Object> apiResult = ApiResult.<Object>builder()
                .success(true)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping
    public ResponseEntity<ApiResult<PagingResult<NotificationRecord>>> findAllNotifications(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) Direction direction
    ) {
        final PaginationRequest paginationRequest = PaginationRequest.of(page, size, sortField, direction);
        PagingResult<NotificationRecord> notificationRecords = notificationService.findAllNotifications(paginationRequest);
        ApiResult<PagingResult<NotificationRecord>> response = ApiResult.<PagingResult<NotificationRecord>>builder()
                .success(true)
                .statusCode(OK)
                .data(notificationRecords)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<ApiResult<NotificationRecord>> findNotification(@PathVariable("notificationId") Long notificationId) {
        NotificationRecord notification = notificationService.findNotification(notificationId);
        ApiResult<NotificationRecord> response = ApiResult.<NotificationRecord>builder()
                .success(true)
                .statusCode(OK)
                .data(notification)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(response);
    }

}
