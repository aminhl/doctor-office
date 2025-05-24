package org.nexthope.doctoroffice.clinic;

import org.nexthope.doctoroffice.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ClinicAlreadyExistsException extends BusinessException {

    public ClinicAlreadyExistsException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }

}
