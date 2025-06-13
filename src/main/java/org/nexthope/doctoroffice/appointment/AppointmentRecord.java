package org.nexthope.doctoroffice.appointment;

import org.nexthope.doctoroffice.user.User;

import java.time.LocalDateTime;

public record AppointmentRecord(Long id,
                                User doctor, // TODO: UserRecord
                                User patient, // TODO: UserRecord
                                LocalDateTime startTime,
                                LocalDateTime endTime,
                                String notes,
                                Status status,
                                LocalDateTime creationDate,
                                LocalDateTime lastModificationDate
) {
    public Appointment toAppointment() {
        return new Appointment()
                .setId(id)
                .setDoctor(doctor)
                .setPatient(patient)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setNotes(notes)
                .setStatus(status)
                .setCreationDate(creationDate)
                .setModificationDate(lastModificationDate);
    }
}
