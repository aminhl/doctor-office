package org.nexthope.doctoroffice.appointment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {

    AppointmentRecord createAppointment(AppointmentRecord appointmentRecord);

    void deleteAppointment(Long appointmentId);

    Page<AppointmentRecord> getAllAppointments(Pageable pageable);

    AppointmentRecord getAppointment(Long appointmentId);

}
