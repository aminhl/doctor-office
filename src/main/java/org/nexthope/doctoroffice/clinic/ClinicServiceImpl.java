package org.nexthope.doctoroffice.clinic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepository clinicRepository;

    private final ClinicMapper clinicMapper;

    public ClinicDTO createClinic(Clinic clinic) {
        log.debug("createClinic - START: Attempting to create clinic [name: {}, address: {}, city: {}]",
                clinic.getName(), clinic.getAddress(), clinic.getCity());
        boolean clinicExists = clinicRepository.existsByNameAndAddressAndCity(
                clinic.getName(), clinic.getAddress(), clinic.getCity());
        if (clinicExists) {
            log.warn("createClinic - Aborted: Clinic already exists [name: {}, address: {}, city: {}]",
                    clinic.getName(), clinic.getAddress(), clinic.getCity());
            throw new ClinicAlreadyExistsException("Clinic " + clinic.getName() + " already exists", CONFLICT);
        }
        var clinicSaved = clinicRepository.save(clinic);
        ClinicDTO clinicDTO = clinicMapper.toDto(clinicSaved);
        log.info("createClinic - Success: Clinic created [name: {}]", clinic.getName());
        log.debug("createClinic - END");
        return clinicDTO;
    }

    public void deleteClinic(Long clinicId) {
        log.debug("deleteClinic - START: Attempting to delete clinic with id [{}]", clinicId);
        boolean clinicExists = clinicRepository.existsById(clinicId);
        if (!clinicExists) {
            log.warn("deleteClinic - Not Found: Clinic with id [{}] does not exist", clinicId);
            throw new ClinicNotFoundException("Clinic with id " + clinicId + " not found", NOT_FOUND);
        }
        clinicRepository.deleteById(clinicId);
        log.info("deleteClinic - Success: Clinic deleted with id [{}]", clinicId);
        log.debug("deleteClinic - END");
    }

    public Page<Clinic> getAllClinics(Pageable pageable) {
        log.debug("findAllClinics - START: Fetching clinics with page number [{}], page size [{}], sort [{}]",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Clinic> result = clinicRepository.findAll(pageable);
        log.info("findAllClinics - Success: Retrieved [{}] clinics (page {} of {})",
                result.getNumberOfElements(), result.getNumber() + 1, result.getTotalPages());
        log.debug("findAllClinics - END");
        return result;
    }

    public Clinic getClinic(Long clinicId) {
        log.debug("findClinicById - START: Fetching clinic with id [{}]", clinicId);
        Clinic result = clinicRepository.findById(clinicId)
                // TODO: more explicit exception
                .orElseThrow(() -> new IllegalArgumentException("Clinic does not exist"));
        log.info("findClinicById - Success: Retrieved clinic with id [{}]", clinicId);
        log.debug("findClinicById - END");
        return result;
    }


}
