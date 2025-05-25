package org.nexthope.doctoroffice.appointment;

import org.nexthope.doctoroffice.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AppointmentAlreadyExistsException extends BusinessException {

    public AppointmentAlreadyExistsException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }

}
