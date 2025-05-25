package org.nexthope.doctoroffice.appointment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {

    AppointmentDTO createAppointment(Appointment appointment);

    void deleteAppointment(Long appointmentId);

    Page<AppointmentDTO> getAllAppointments(Pageable pageable);

    AppointmentDTO getAppointment(Long appointmentId);

}
