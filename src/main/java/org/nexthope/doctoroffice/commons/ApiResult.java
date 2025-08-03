package org.nexthope.doctoroffice.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResult<T>(
        boolean success,
        String errorMessage,
        HttpStatus statusCode,
        T data,
        Instant timestamp
        ) {
}
