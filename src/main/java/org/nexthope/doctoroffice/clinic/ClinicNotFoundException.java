package org.nexthope.doctoroffice.clinic;

import org.nexthope.doctoroffice.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ClinicNotFoundException extends BusinessException {

    public ClinicNotFoundException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }

}
