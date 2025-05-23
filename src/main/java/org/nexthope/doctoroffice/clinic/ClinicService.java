package org.nexthope.doctoroffice.clinic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClinicService {

    ClinicDTO createClinic(Clinic clinic);

    void deleteClinic(Long clinicId);

    Page<Clinic> getAllClinics(Pageable pageable);

    Clinic getClinic(Long clinicId);

}
