package org.nexthope.doctoroffice.medicalreport;

import org.nexthope.doctoroffice.commons.PaginationRequest;
import org.nexthope.doctoroffice.commons.PagingResult;

public interface MedicalReportService {

    MedicalReportRecord createMedicalReport(MedicalReportRecord MedicalReportRecord);

    void deleteMedicalReport(Long medicalReportId);

    PagingResult<MedicalReportRecord> findAllMedicalReports(PaginationRequest paginationRequest);

    MedicalReportRecord findMedicalReport(Long medicalReportId);

}
