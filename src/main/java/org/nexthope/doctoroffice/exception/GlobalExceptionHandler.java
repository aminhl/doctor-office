package org.nexthope.doctoroffice.exception;

import lombok.extern.slf4j.Slf4j;
import org.nexthope.doctoroffice.commons.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.Instant.now;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException e) {
        log.error("{} occurred: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .errorMessage(e.getMessage())
                .statusCode(e.getErrorCode())
                .timestamp(now())
                .build();
        return ResponseEntity.status(e.getErrorCode()).body(response);
    }

}
