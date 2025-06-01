package org.nexthope.doctoroffice.exception;

import lombok.extern.slf4j.Slf4j;
import org.nexthope.doctoroffice.appointment.AppointmentAlreadyExistsException;
import org.nexthope.doctoroffice.appointment.AppointmentNotFoundException;
import org.nexthope.doctoroffice.clinic.ClinicAlreadyExistsException;
import org.nexthope.doctoroffice.clinic.ClinicNotFoundException;
import org.nexthope.doctoroffice.commons.ApiResponse;
import org.nexthope.doctoroffice.medicalrecord.MedicalRecordAlreadyExistsException;
import org.nexthope.doctoroffice.medicalrecord.MedicalRecordNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.Instant.now;

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
                .timestamp(now())
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
                .timestamp(now())
                .build();
        return ResponseEntity.status(e.getErrorCode()).body(apiResponse);
    }

    @ExceptionHandler(AppointmentAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleAppointmentAlreadyExistsException(AppointmentAlreadyExistsException e) {
        log.error("AppointmentAlreadyExistsException occurred: {}", e.getMessage(), e);
        ApiResponse<Object> apiResponse = ApiResponse.<Object>builder()
                .success(false)
                .message(e.getMessage())
                .statusCode(e.getErrorCode())
                .timestamp(now())
                .build();
        return ResponseEntity.status(e.getErrorCode()).body(apiResponse);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<Object> handleAppointmentNotFoundException(AppointmentNotFoundException e) {
        log.error("AppointmentNotFoundException occurred: {}", e.getMessage(), e);
        ApiResponse<Object> apiResponse = ApiResponse.<Object>builder()
                .success(false)
                .message(e.getMessage())
                .statusCode(e.getErrorCode())
                .timestamp(now())
                .build();
        return ResponseEntity.status(e.getErrorCode()).body(apiResponse);
    }

    @ExceptionHandler(MedicalRecordAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleMedicalRecordAlreadyExistsException(MedicalRecordAlreadyExistsException e) {
        log.error("MedicalRecordAlreadyExistsException occurred: {}", e.getMessage(), e);
        ApiResponse<Object> apiResponse = ApiResponse.<Object>builder()
                .success(false)
                .message(e.getMessage())
                .statusCode(e.getErrorCode())
                .timestamp(now())
                .build();
        return ResponseEntity.status(e.getErrorCode()).body(apiResponse);
    }

    @ExceptionHandler(MedicalRecordNotFoundException.class)
    public ResponseEntity<Object> handleMedicalRecordNotFoundException(MedicalRecordNotFoundException e) {
        log.error("MedicalRecordNotFoundException occurred: {}", e.getMessage(), e);
        ApiResponse<Object> apiResponse = ApiResponse.<Object>builder()
                .success(false)
                .message(e.getMessage())
                .statusCode(e.getErrorCode())
                .timestamp(now())
                .build();
        return ResponseEntity.status(e.getErrorCode()).body(apiResponse);
    }


}
