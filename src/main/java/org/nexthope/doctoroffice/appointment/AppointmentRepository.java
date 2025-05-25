package org.nexthope.doctoroffice.appointment;

import org.nexthope.doctoroffice.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorAndPatientAndStartTimeAndEndTime(User doctor, User patient, LocalDateTime startTime, LocalDateTime endTime);

}
