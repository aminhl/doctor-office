package org.nexthope.doctoroffice.medicalrecord;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.nexthope.doctoroffice.commons.BaseAudit;
import org.nexthope.doctoroffice.user.User;

import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "medical_record")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicalRecord extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical_record_seq")
    @SequenceGenerator(name = "medical_record_seq", sequenceName = "medical_record_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "patient_id")
    private User patient;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "diagnosis", nullable = false)
    private String diagnosis;

    @Column(name = "treatment", nullable = false)
    private String treatment;

    @Column(name = "prescription", nullable = false)
    private String prescription;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MedicalRecord that = (MedicalRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(patient, that.patient) && Objects.equals(doctor, that.doctor) && Objects.equals(date, that.date) && Objects.equals(diagnosis, that.diagnosis) && Objects.equals(treatment, that.treatment) && Objects.equals(prescription, that.prescription) && Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patient, doctor, date, diagnosis, treatment, prescription, notes);
    }
}
