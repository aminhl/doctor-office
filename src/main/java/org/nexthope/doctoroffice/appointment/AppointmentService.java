package org.nexthope.doctoroffice.appointment;

import org.nexthope.doctoroffice.commons.PaginationRequest;
import org.nexthope.doctoroffice.commons.PagingResult;

public interface AppointmentService {

    AppointmentRecord createAppointment(AppointmentRecord appointmentRecord);

    void deleteAppointment(Long appointmentId);

    PagingResult<AppointmentRecord> findAllAppointments(PaginationRequest paginationRequest);

    AppointmentRecord findAppointment(Long appointmentId);

}
