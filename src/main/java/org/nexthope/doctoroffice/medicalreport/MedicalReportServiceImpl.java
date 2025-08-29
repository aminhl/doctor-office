package org.nexthope.doctoroffice.medicalreport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nexthope.doctoroffice.commons.pagination.PaginationRequest;
import org.nexthope.doctoroffice.commons.pagination.PaginationUtils;
import org.nexthope.doctoroffice.commons.pagination.PagingResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalReportServiceImpl implements MedicalReportService {

    private final MedicalReportRepository medicalReportRepository;

    @Override
    public MedicalReportDTO create(MedicalReportDTO medicalReportDTO) {
        log.debug("createMedicalRecord - Start: Attempting to create medical report { patient:{}, doctor:{}, date:{} }",
                format("%s %s", medicalReportDTO.patient().getFirstName(), medicalReportDTO.patient().getLastName()),
                format("%s %s", medicalReportDTO.doctor().getFirstName(), medicalReportDTO.doctor().getLastName()),
                medicalReportDTO.date());
        boolean medicalRecordExists = medicalReportRepository.existsByPatientAndDoctorAndDate(medicalReportDTO.patient(),
                medicalReportDTO.doctor(), medicalReportDTO.date());
        if (medicalRecordExists) {
            log.warn("createMedicalRecord - Aborted: Medical report already exists { patient:{}, doctor:{}, date:{} }",
                    format("%s %s", medicalReportDTO.patient().getFirstName(), medicalReportDTO.patient().getLastName()), format("%s %s", medicalReportDTO.doctor().getFirstName(),
                            medicalReportDTO.doctor().getLastName()), medicalReportDTO.date());
            throw new MedicalReportAlreadyExistsException(format("Medical report %s %s %s already exists", format("%s %s", medicalReportDTO.patient().getFirstName(), medicalReportDTO.patient().getLastName()),
                    format("%s %s", medicalReportDTO.doctor().getFirstName(), medicalReportDTO.doctor().getLastName()), medicalReportDTO.date()), CONFLICT);
        }
        var medicalRecordSaved = medicalReportRepository.save(medicalReportDTO.toMedicalReport()).toRecord();
        log.info("createMedicalRecord - Success: Medical report created { patient:{}, doctor:{}, date:{} }",
                format("%s %s", medicalReportDTO.patient().getFirstName(), medicalReportDTO.patient().getLastName()), format("%s %s", medicalReportDTO.doctor().getFirstName(),
                        medicalReportDTO.doctor().getLastName()), medicalReportDTO.date());
        log.debug("createMedicalRecord - End");
        return medicalRecordSaved;
    }

    @Override
    public void delete(Long medicalReportId) {
        log.debug("deleteMedicalRecord - Start: Attempting to delete medical report with id:{}", medicalReportId);
        boolean medicalRecordExists = medicalReportRepository.existsById(medicalReportId);
        if (!medicalRecordExists) {
            log.warn("deleteMedicalRecord - Not found: Medical report with id:{} does not exist", medicalReportId);
            throw new MedicalReportNotFoundException(format("Medical report with id %s not found", medicalReportId), NOT_FOUND);
        }
        medicalReportRepository.deleteById(medicalReportId);
        log.info("deleteMedicalRecord - Success: Medical report deleted with id:{}", medicalReportId);
        log.debug("deleteMedicalRecord - End");
    }

    @Override
    public PagingResult<MedicalReportDTO> findAll(PaginationRequest paginationRequest) {
        final Pageable pageable = PaginationUtils.getPageable(paginationRequest);
        log.debug("getAllMedicalRecords - Start: Fetching medical reports with page_number:{}, page_size:{}, sort:{}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<MedicalReportDTO> medicalReportRecords = medicalReportRepository.findAll(pageable)
                .map(MedicalReport::toRecord);
        log.info("getAllMedicalRecords - Success: Retrieved {} medical report(s) (page {} of {})",
                medicalReportRecords.getNumberOfElements(), medicalReportRecords.getNumber()+1, medicalReportRecords.getTotalPages());
        log.debug("getAllMedicalRecords - End");
        return new PagingResult<>(
                medicalReportRecords.stream().toList(),
                medicalReportRecords.getTotalPages(),
                medicalReportRecords.getNumberOfElements(),
                medicalReportRecords.getSize(),
                medicalReportRecords.getNumber(),
                medicalReportRecords.isEmpty()
        );
    }

    @Override
    public MedicalReportDTO find(Long medicalReportId) {
        log.debug("getMedicalRecord - Start: Fetching medical report with id:{}", medicalReportId);
        MedicalReportDTO MedicalReportDTO = medicalReportRepository.findById(medicalReportId)
                .map(MedicalReport::toRecord)
                .orElseThrow(() -> new MedicalReportNotFoundException(format("Medical report with id %s not found", medicalReportId), NOT_FOUND));
        log.info("getMedicalRecord - Success: Retrieved medical report with id:{}", medicalReportId);
        log.debug("getMedicalRecord - End");
        return MedicalReportDTO;
    }

}
