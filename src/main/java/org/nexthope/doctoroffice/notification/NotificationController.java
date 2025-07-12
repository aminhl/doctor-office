package org.nexthope.doctoroffice.notification;

import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResponse;
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
    public ResponseEntity<ApiResponse<NotificationRecord>> createNotification(@RequestBody @Validated NotificationRecord notificationRecord) {
        var notificationSaved = notificationService.createNotification(notificationRecord);
        ApiResponse<NotificationRecord> apiResponse = ApiResponse.<NotificationRecord>builder()
                .success(true)
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
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagingResult<NotificationRecord>>> findAllNotifications(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) Direction direction
    ) {
        final PaginationRequest paginationRequest = PaginationRequest.of(page, size, sortField, direction);
        PagingResult<NotificationRecord> notificationRecords = notificationService.findAllNotifications(paginationRequest);
        ApiResponse<PagingResult<NotificationRecord>> response = ApiResponse.<PagingResult<NotificationRecord>>builder()
                .success(true)
                .statusCode(OK)
                .data(notificationRecords)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<NotificationRecord>> findNotification(@PathVariable("notificationId") Long notificationId) {
        NotificationRecord notification = notificationService.findNotification(notificationId);
        ApiResponse<NotificationRecord> response = ApiResponse.<NotificationRecord>builder()
                .success(true)
                .statusCode(OK)
                .data(notification)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(response);
    }

}
