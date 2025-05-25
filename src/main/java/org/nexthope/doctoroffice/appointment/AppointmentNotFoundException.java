package org.nexthope.doctoroffice.appointment;

import org.nexthope.doctoroffice.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AppointmentNotFoundException extends BusinessException {

    public AppointmentNotFoundException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }

}
