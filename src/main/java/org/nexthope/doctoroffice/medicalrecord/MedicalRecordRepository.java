package org.nexthope.doctoroffice.medicalrecord;

import org.nexthope.doctoroffice.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    boolean existsByPatientAndDoctorAndDate(User patient, User doctor, LocalDate date);

}
