package org.nexthope.doctoroffice.medicalrecord;

import org.nexthope.doctoroffice.user.User;

import java.time.LocalDate;

public record MedicalRecordDTO(
        Long id,
        User patient,
        User doctor,
        LocalDate date,
        String diagnosis,
        String treatment,
        String prescription,
        String notes
        ) {
}
