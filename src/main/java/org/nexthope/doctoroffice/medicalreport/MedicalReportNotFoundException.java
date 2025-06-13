package org.nexthope.doctoroffice.medicalreport;

import org.nexthope.doctoroffice.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class MedicalReportNotFoundException extends BusinessException {

    public MedicalReportNotFoundException(String message, HttpStatus errorCode) {
        super(message, errorCode);
    }

}
