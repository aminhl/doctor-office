package org.nexthope.doctoroffice.notification;

import org.nexthope.doctoroffice.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class NotificationNotFoundException extends BusinessException {

    public NotificationNotFoundException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }

}
