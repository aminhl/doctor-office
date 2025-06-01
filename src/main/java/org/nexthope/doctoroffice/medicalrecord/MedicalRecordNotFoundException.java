package org.nexthope.doctoroffice.medicalrecord;

import org.nexthope.doctoroffice.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class MedicalRecordNotFoundException extends BusinessException {

    public MedicalRecordNotFoundException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }

}
