package org.nexthope.doctoroffice.appointment;

import org.nexthope.doctoroffice.commons.pagination.PaginationRequest;
import org.nexthope.doctoroffice.commons.pagination.PagingResult;

public interface AppointmentService {

    AppointmentDTO create(AppointmentDTO appointmentDTO);

    void delete(Long appointmentId);

    PagingResult<AppointmentDTO> findAll(PaginationRequest paginationRequest);

    AppointmentDTO find(Long appointmentId);

}
