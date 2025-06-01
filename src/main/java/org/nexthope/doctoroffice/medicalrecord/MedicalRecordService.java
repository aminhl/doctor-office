package org.nexthope.doctoroffice.medicalrecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicalRecordService {

    MedicalRecordDTO createMedicalRecord(MedicalRecord medicalRecord);

    void deleteMedicalRecord(Long medicalRecordId);

    Page<MedicalRecordDTO> getAllMedicalRecords(Pageable pageable);

    MedicalRecordDTO getMedicalRecord(Long medicalRecordId);

}
