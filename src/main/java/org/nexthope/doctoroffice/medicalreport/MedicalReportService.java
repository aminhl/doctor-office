package org.nexthope.doctoroffice.medicalreport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicalReportService {

    MedicalReportRecord createMedicalRecord(MedicalReportRecord MedicalReportRecord);

    void deleteMedicalRecord(Long medicalRecordId);

    Page<MedicalReportRecord> getAllMedicalRecords(Pageable pageable);

    MedicalReportRecord getMedicalRecord(Long medicalRecordId);

}
