package org.nexthope.doctoroffice.medicalreport;

import org.nexthope.doctoroffice.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MedicalReportRecord(
        Long id,
        User patient,
        User doctor,
        LocalDate date,
        String diagnosis,
        String treatment,
        String prescription,
        String notes,
        LocalDateTime creationDate,
        LocalDateTime lastModificationDate
) {

    public MedicalReport toMedicalReport() {
        return new MedicalReport()
                .setId(id)
                .setDoctor(doctor)
                .setPatient(patient)
                .setDate(date)
                .setDiagnosis(diagnosis)
                .setTreatment(treatment)
                .setPrescription(prescription)
                .setNotes(notes)
                .setCreationDate(creationDate)
                .setModificationDate(lastModificationDate);
    }

}
