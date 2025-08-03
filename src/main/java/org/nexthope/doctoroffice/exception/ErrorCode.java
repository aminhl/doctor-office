package org.nexthope.doctoroffice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USERNAME_NOT_FOUND("ERR_USERNAME_NOT_FOUND", "Cannot find user with provided username", HttpStatus.NOT_FOUND);

    private final String code;
    private final String defaultMessage;
    private final HttpStatus status;

}