package org.nexthope.doctoroffice.handlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String code;
    private String message;
    private List<ValidationError> validationErrors;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ValidationError {
        private String field;
        private String code;
        private String message;
    }

}
