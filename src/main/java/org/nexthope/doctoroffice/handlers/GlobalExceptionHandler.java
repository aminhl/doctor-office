package org.nexthope.doctoroffice.handlers;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.nexthope.doctoroffice.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Hidden
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        log.error("{} occurred: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getErrorCode().toString())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(e.getErrorCode()).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(final ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        List<ErrorResponse.ValidationError> errors = e.getConstraintViolations()
                .stream()
                .map(cv -> {
                    String field = StreamSupport
                            .stream(cv.getPropertyPath().spliterator(), false)
                            .reduce((first, second) -> second)
                            .map(Path.Node::getName)
                            .orElse("parameter");
                    String code = Optional.ofNullable(cv.getConstraintDescriptor().getAnnotation())
                            .map(annotation -> annotation.annotationType().getSimpleName().toLowerCase())
                            .orElse("constraint_violation");
                    String message = cv.getMessage();
                    return ErrorResponse.ValidationError.builder()
                            .field(field)
                            .code(code)
                            .message(message)
                            .build();
                })
                .toList();
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code("validation_error")
                .message("Validation failed")
                .validationErrors(errors).build();
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(final IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        final ErrorResponse errorResponse = ErrorResponse
                .builder()
                .code("illegal_argument")
                .message(e.getMessage())
                .build();
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(final Exception e) {
        log.error(e.getMessage(), e);
        final ErrorResponse errorResponse = ErrorResponse
                .builder()
                .code("internal_server_error")
                .message(e.getMessage())
                .build();
        return ResponseEntity
                .internalServerError()
                .body(errorResponse);
    }

}
