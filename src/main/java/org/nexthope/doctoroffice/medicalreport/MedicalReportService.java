package org.nexthope.doctoroffice.medicalreport;

import org.nexthope.doctoroffice.commons.pagination.PaginationRequest;
import org.nexthope.doctoroffice.commons.pagination.PagingResult;

public interface MedicalReportService {

    MedicalReportDTO create(MedicalReportDTO MedicalReportDTO);

    void delete(Long medicalReportId);

    PagingResult<MedicalReportDTO> findAll(PaginationRequest paginationRequest);

    MedicalReportDTO find(Long medicalReportId);

}
