package org.nexthope.doctoroffice.appointment;

import org.nexthope.doctoroffice.commons.PaginationRequest;
import org.nexthope.doctoroffice.commons.PagingResult;

public interface AppointmentService {

    AppointmentRecord create(AppointmentRecord appointmentRecord);

    void delete(Long appointmentId);

    PagingResult<AppointmentRecord> findAll(PaginationRequest paginationRequest);

    AppointmentRecord find(Long appointmentId);

}
