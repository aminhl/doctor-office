package org.nexthope.doctoroffice.notification;

import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResult;
import org.nexthope.doctoroffice.commons.pagination.PaginationRequest;
import org.nexthope.doctoroffice.commons.pagination.PagingResult;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.time.Instant.now;
import static org.nexthope.doctoroffice.notification.NotificationConstants.NOTIFICATION_ENDPOINT;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(NOTIFICATION_ENDPOINT)
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResult<NotificationDTO>> create(@RequestBody @Validated NotificationDTO notificationDTO) {
        var notificationSaved = notificationService.create(notificationDTO);
        ApiResult<NotificationDTO> apiResult = ApiResult.<NotificationDTO>builder()
                .success(true)
                .timestamp(now())
                .data(notificationSaved)
                .build();
        return ResponseEntity.status(CREATED).body(apiResult);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> delete(@PathVariable("notificationId") Long notificationId) {
        notificationService.delete(notificationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ApiResult<PagingResult<NotificationDTO>>> findAll(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(defaultValue = "DESC", required = false) Direction direction,
            @RequestParam(defaultValue = "id", required = false) String sortField
    ) {
        final PaginationRequest paginationRequest = PaginationRequest.of(page, size, direction, sortField);
        PagingResult<NotificationDTO> notificationRecords = notificationService.findAll(paginationRequest);
        ApiResult<PagingResult<NotificationDTO>> response = ApiResult.<PagingResult<NotificationDTO>>builder()
                .success(true)
                .timestamp(now())
                .data(notificationRecords)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<ApiResult<NotificationDTO>> find(@PathVariable("notificationId") Long notificationId) {
        NotificationDTO notification = notificationService.find(notificationId);
        ApiResult<NotificationDTO> response = ApiResult.<NotificationDTO>builder()
                .success(true)
                .timestamp(now())
                .data(notification)
                .build();
        return ResponseEntity.ok(response);
    }

}
