package org.nexthope.doctoroffice.exception;

import lombok.extern.slf4j.Slf4j;
import org.nexthope.doctoroffice.clinic.ClinicAlreadyExistsException;
import org.nexthope.doctoroffice.clinic.ClinicNotFoundException;
import org.nexthope.doctoroffice.commons.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ClinicAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleClinicAlreadyExistsException(ClinicAlreadyExistsException e) {
        log.error("ClinicAlreadyExistsException occurred: {}", e.getMessage(), e);
        ApiResponse<Object> apiResponse = ApiResponse.<Object>builder()
                .success(false)
                .message(e.getMessage())
                .statusCode(e.getErrorCode())
                .build();
        return ResponseEntity.status(e.getErrorCode()).body(apiResponse);
    }

    @ExceptionHandler(ClinicNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleClinicNotFoundException(ClinicNotFoundException e) {
        log.error("ClinicNotFoundException occurred: {}", e.getMessage(), e);
        ApiResponse<Object> apiResponse = ApiResponse.<Object>builder()
                .success(false)
                .message(e.getMessage())
                .statusCode(e.getErrorCode())
                .build();
        return ResponseEntity.status(e.getErrorCode()).body(apiResponse);
    }

}
