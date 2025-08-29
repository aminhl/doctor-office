package org.nexthope.doctoroffice.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.Instant;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResult<T>(
        boolean success,
        Instant timestamp,
        T data
        ) {
}
