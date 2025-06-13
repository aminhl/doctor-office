package org.nexthope.doctoroffice.medicalreport;

import org.nexthope.doctoroffice.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MedicalReportRepository extends JpaRepository<MedicalReport, Long> {

    boolean existsByPatientAndDoctorAndDate(User patient, User doctor, LocalDate date);

}
