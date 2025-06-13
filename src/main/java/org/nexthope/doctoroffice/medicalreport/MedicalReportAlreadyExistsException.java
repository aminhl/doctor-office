package org.nexthope.doctoroffice.medicalreport;

import org.nexthope.doctoroffice.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class MedicalReportAlreadyExistsException extends BusinessException {

    public MedicalReportAlreadyExistsException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }

}
