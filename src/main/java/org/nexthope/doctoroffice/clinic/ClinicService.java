package org.nexthope.doctoroffice.clinic;

import org.nexthope.doctoroffice.commons.PaginationRequest;
import org.nexthope.doctoroffice.commons.PagingResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClinicService {

    ClinicRecord createClinic(ClinicRecord clinicRecord);

    void deleteClinic(Long clinicId);

    PagingResult<ClinicRecord> findAllClinics(PaginationRequest paginationRequest);

    ClinicRecord findClinic(Long clinicId);

}
