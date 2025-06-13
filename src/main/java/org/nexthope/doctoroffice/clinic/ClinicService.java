package org.nexthope.doctoroffice.clinic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClinicService {

    ClinicRecord createClinic(ClinicRecord clinicRecord);

    void deleteClinic(Long clinicId);

    Page<ClinicRecord> getAllClinics(Pageable pageable);

    ClinicRecord getClinic(Long clinicId);

}
