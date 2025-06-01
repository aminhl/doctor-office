package org.nexthope.doctoroffice.medicalrecord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordServiceImpl implements MedicalRecordService{

    private final MedicalRecordRepository medicalRecordRepository;

    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    public MedicalRecordDTO createMedicalRecord(MedicalRecord medicalRecord) {
        log.debug("createMedicalRecord - Start: Attempting to create medical record [patient: {}, doctor: {}, date: {}, diagnosis: {}, treatment: {}, prescription: {}, notes: {}]",
               format("%s %s", medicalRecord.getPatient().getFirstName(), medicalRecord.getPatient().getLastName()), format("%s %s", medicalRecord.getDoctor().getFirstName(), medicalRecord.getDoctor().getLastName()), medicalRecord.getDate(), medicalRecord.getDiagnosis(), medicalRecord.getTreatment(), medicalRecord.getPrescription(), medicalRecord.getNotes());
        boolean medicalRecordExists = medicalRecordRepository.existsByPatientAndDoctorAndDate(medicalRecord.getPatient(), medicalRecord.getDoctor(), medicalRecord.getDate());
        if (medicalRecordExists) {
            log.warn("createMedicalRecord - Aborted: Medical record already exists [patient: {}, doctor: {}, date: {}, diagnosis: {}, treatment: {}, prescription: {}, notes: {}]",
                    format("%s %s", medicalRecord.getPatient().getFirstName(), medicalRecord.getPatient().getLastName()), format("%s %s", medicalRecord.getDoctor().getFirstName(), medicalRecord.getDoctor().getLastName()), medicalRecord.getDate(), medicalRecord.getDiagnosis(), medicalRecord.getTreatment(), medicalRecord.getPrescription(), medicalRecord.getNotes());
            throw new MedicalRecordAlreadyExistsException(format("Medical record %s %s %s already exists",  format("%s %s", medicalRecord.getPatient().getFirstName(), medicalRecord.getPatient().getLastName()), format("%s %s", medicalRecord.getDoctor().getFirstName(), medicalRecord.getDoctor().getLastName()), medicalRecord.getDate()), CONFLICT);
        }
        MedicalRecord medicalRecordSaved = medicalRecordRepository.save(medicalRecord);
        MedicalRecordDTO medicalRecordDTO = medicalRecordMapper.toDto(medicalRecordSaved);
        log.info("createMedicalRecord - Success: Medical record created [patient: {}, doctor: {}, date: {}, diagnosis: {}, treatment: {}, prescription: {}, notes: {}]",
                format("%s %s", medicalRecord.getPatient().getFirstName(), medicalRecord.getPatient().getLastName()), format("%s %s", medicalRecord.getDoctor().getFirstName(), medicalRecord.getDoctor().getLastName()), medicalRecord.getDate(), medicalRecord.getDiagnosis(), medicalRecord.getTreatment(), medicalRecord.getPrescription(), medicalRecord.getNotes());
        log.debug("createMedicalRecord - End");
        return medicalRecordDTO;
    }

    @Override
    public void deleteMedicalRecord(Long medicalRecordId) {
        log.debug("deleteMedicalRecord - Start: Attempting to delete medical record with id [{}]", medicalRecordId);

        boolean medicalRecordExists = medicalRecordRepository.existsById(medicalRecordId);
        if (!medicalRecordExists) {
            log.warn("deleteMedicalRecord - Not found: Medical record with id [{}] does not exist", medicalRecordId);
            throw new MedicalRecordNotFoundException(format("Medical record with id %s not found", medicalRecordId), NOT_FOUND);
        }
        medicalRecordRepository.deleteById(medicalRecordId);
        log.info("deleteMedicalRecord - Success: Medical record deleted with id [{}]", medicalRecordId);
        log.debug("deleteMedicalRecord - End");
    }

    @Override
    public Page<MedicalRecordDTO> getAllMedicalRecords(Pageable pageable) {
        log.debug("getAllMedicalRecords - Start: Fetching medical records with page number [{}], page size [{}], sort: [{}]",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<MedicalRecordDTO> medicalRecordDTOS = medicalRecordRepository.findAll(pageable)
                .map(medicalRecordMapper::toDto);
        log.info("getAllMedicalRecords - Success: Retrieved medical records [{}] (page {} of {})", medicalRecordDTOS.getNumberOfElements(), medicalRecordDTOS.getNumber(), medicalRecordDTOS.getTotalPages() + 1);
        log.debug("getAllMedicalRecords - End");
        return medicalRecordDTOS;
    }

    @Override
    public MedicalRecordDTO getMedicalRecord(Long medicalRecordId) {
        log.debug("getMedicalRecord - Start: Fetching medical record with id [{}]", medicalRecordId);
        MedicalRecordDTO medicalRecordDTO = medicalRecordRepository.findById(medicalRecordId)
                .map(medicalRecordMapper::toDto)
                .orElseThrow(() -> new MedicalRecordNotFoundException(format("Medical record with id %s not found", medicalRecordId), NOT_FOUND));
        log.info("getMedicalRecord - Success: Retrieved medical record with id [{}]", medicalRecordId);
        log.debug("getMedicalRecord - End");
        return medicalRecordDTO;
    }

}
