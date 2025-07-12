package org.nexthope.doctoroffice.clinic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nexthope.doctoroffice.commons.PaginationRequest;
import org.nexthope.doctoroffice.commons.PaginationUtils;
import org.nexthope.doctoroffice.commons.PagingResult;
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

    @Override
    public ClinicRecord createClinic(ClinicRecord clinicRecord) {
        log.debug("createClinic - Start: Attempting to create clinic: name:{}, address:{}, city:{}",
                clinicRecord.name(), clinicRecord.address(), clinicRecord.city());
        boolean clinicExists = clinicRepository.existsByNameAndAddressAndCity(
                clinicRecord.name(), clinicRecord.address(), clinicRecord.city());
        if (clinicExists) {
            log.warn("createClinic - Aborted: Clinic already exists name:{}, address:{}, city:{}",
                    clinicRecord.name(), clinicRecord.address(), clinicRecord.city());
            throw new ClinicAlreadyExistsException("Clinic " + clinicRecord.name() + " already exists", CONFLICT);
        }
        var clinicSaved = clinicRepository.save(clinicRecord.toClinic()).toRecord();
        log.info("createClinic - Success: Clinic created name:{}", clinicRecord.name());
        log.debug("createClinic - End");
        return clinicSaved;
    }

    @Override
    public void deleteClinic(Long clinicId) {
        log.debug("deleteClinic - Start: Attempting to delete clinic with id:{}", clinicId);
        boolean clinicExists = clinicRepository.existsById(clinicId);
        if (!clinicExists) {
            log.warn("deleteClinic - Not Found: Clinic with id:{} does not exist", clinicId);
            throw new ClinicNotFoundException("Clinic with id " + clinicId + " not found", NOT_FOUND);
        }
        clinicRepository.deleteById(clinicId);
        log.info("deleteClinic - Success: Clinic deleted with id:{}", clinicId);
        log.debug("deleteClinic - End");
    }

    @Override
    public PagingResult<ClinicRecord> findAllClinics(PaginationRequest paginationRequest) {
        final Pageable pageable = PaginationUtils.getPageable(paginationRequest);
        log.info("getAllClinics - Start: Fetching clinics with page-number:{}, page-size:{}, sort:{}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<ClinicRecord> clinicsRecords = clinicRepository.findAll(pageable)
                .map(Clinic::toRecord);
        log.info("getAllClinics - Success: Retrieved:{} clinic(s) (page:{} of:{})",
                clinicsRecords.getNumberOfElements(), clinicsRecords.getNumber()+1, clinicsRecords.getTotalPages());
        log.debug("getAllClinics - End");
        return new PagingResult<ClinicRecord>(
                clinicsRecords.stream().toList(),
                clinicsRecords.getTotalPages(),
                clinicsRecords.getTotalElements(),
                clinicsRecords.getSize(),
                clinicsRecords.getNumber(),
                clinicsRecords.isEmpty()
        );
    }

    @Override
    public ClinicRecord findClinic(Long clinicId) {
        log.debug("getClinic - Start: Fetching clinic with id:{}", clinicId);
        ClinicRecord result = clinicRepository.findById(clinicId)
                .map(Clinic::toRecord)
                .orElseThrow(() -> new ClinicNotFoundException("Clinic with id " + clinicId + " not found", NOT_FOUND));
        log.info("getClinic - Success: Retrieved clinic with id:{}", clinicId);
        log.debug("getClinic - End");
        return result;
    }

}
