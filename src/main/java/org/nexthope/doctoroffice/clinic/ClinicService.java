package org.nexthope.doctoroffice.clinic;

import org.nexthope.doctoroffice.commons.pagination.PaginationRequest;
import org.nexthope.doctoroffice.commons.pagination.PagingResult;

public interface ClinicService {

    ClinicDTO create(ClinicDTO clinicDTO);

    void delete(Long clinicId);

    PagingResult<ClinicDTO> findAll(PaginationRequest paginationRequest);

    ClinicDTO find(Long clinicId);

}
