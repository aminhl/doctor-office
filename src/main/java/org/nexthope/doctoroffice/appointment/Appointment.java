package org.nexthope.doctoroffice.appointment;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.nexthope.doctoroffice.commons.BaseAudit;
import org.nexthope.doctoroffice.user.User;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@Entity(name = "appointment")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Appointment extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_seq")
    @SequenceGenerator(name = "appointment_seq", sequenceName = "appointment_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "patient_id")
    private User patient;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "notes", nullable = false, columnDefinition = "TEXT")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id) && Objects.equals(doctor, that.doctor) && Objects.equals(patient, that.patient) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(notes, that.notes) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doctor, patient, startTime, endTime, notes, status);
    }

    public Appointment setCreationDate(LocalDateTime creationDate) {
        super.setCreationDate(creationDate);
        return this;
    }

    public Appointment setModificationDate(LocalDateTime modificationDate) {
        super.setModificationDate(modificationDate);
        return this;
    }

    public AppointmentRecord toRecord() {
        return new AppointmentRecord(id, doctor, patient, startTime, endTime, notes, status, getCreationDate(), getModificationDate());
    }

}
