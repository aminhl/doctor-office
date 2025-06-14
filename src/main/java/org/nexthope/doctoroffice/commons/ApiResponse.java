package org.nexthope.doctoroffice.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,
        String message,
        HttpStatus statusCode,
        String errorMessage, // TODO: use it in the global exception handler for more details
        T data,
        Instant timestamp
        ) {
}
