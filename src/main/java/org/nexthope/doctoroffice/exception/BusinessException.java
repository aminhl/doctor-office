package org.nexthope.doctoroffice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus errorCode;

    public BusinessException(String message, HttpStatus errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
