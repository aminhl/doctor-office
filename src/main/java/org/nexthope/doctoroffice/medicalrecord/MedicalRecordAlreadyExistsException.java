package org.nexthope.doctoroffice.medicalrecord;

import org.nexthope.doctoroffice.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class MedicalRecordAlreadyExistsException extends BusinessException {

    public MedicalRecordAlreadyExistsException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }

}
