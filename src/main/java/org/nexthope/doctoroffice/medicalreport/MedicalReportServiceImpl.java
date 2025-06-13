package org.nexthope.doctoroffice.medicalreport;

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
public class MedicalReportServiceImpl implements MedicalReportService {

    private final MedicalReportRepository medicalReportRepository;


    @Override
    public MedicalReportRecord createMedicalRecord(MedicalReportRecord medicalReportRecord) {
        log.debug("createMedicalRecord - Start: Attempting to create medical record [patient: {}, doctor: {}, date: {}, diagnosis: {}, treatment: {}, prescription: {}, notes: {}]",
                format("%s %s", medicalReportRecord.patient().getFirstName(), medicalReportRecord.patient().getLastName()),
                format("%s %s", medicalReportRecord.doctor().getFirstName(), medicalReportRecord.doctor().getLastName()),
                medicalReportRecord.date(), medicalReportRecord.diagnosis(), medicalReportRecord.treatment(),
                medicalReportRecord.prescription(), medicalReportRecord.notes());
        boolean medicalRecordExists = medicalReportRepository.existsByPatientAndDoctorAndDate(medicalReportRecord.patient(),
                medicalReportRecord.doctor(), medicalReportRecord.date());
        if (medicalRecordExists) {
            log.warn("createMedicalRecord - Aborted: Medical record already exists [patient: {}, doctor: {}, date: {}, diagnosis: {}, treatment: {}, prescription: {}, notes: {}]",
                    format("%s %s", medicalReportRecord.patient().getFirstName(), medicalReportRecord.patient().getLastName()), format("%s %s", medicalReportRecord.doctor().getFirstName(),
                            medicalReportRecord.doctor().getLastName()), medicalReportRecord.date(), medicalReportRecord.diagnosis(), medicalReportRecord.treatment(), medicalReportRecord.prescription(),
                    medicalReportRecord.notes());
            throw new MedicalReportAlreadyExistsException(format("Medical record %s %s %s already exists", format("%s %s", medicalReportRecord.patient().getFirstName(), medicalReportRecord.patient().getLastName()),
                    format("%s %s", medicalReportRecord.doctor().getFirstName(), medicalReportRecord.doctor().getLastName()), medicalReportRecord.date()), CONFLICT);
        }
        var medicalRecordSaved = medicalReportRepository.save(medicalReportRecord.toMedicalReport()).toRecord();
        log.info("createMedicalRecord - Success: Medical record created [patient: {}, doctor: {}, date: {}, diagnosis: {}, treatment: {}, prescription: {}, notes: {}]",
                format("%s %s", medicalReportRecord.patient().getFirstName(), medicalReportRecord.patient().getLastName()), format("%s %s", medicalReportRecord.doctor().getFirstName(),
                        medicalReportRecord.doctor().getLastName()), medicalReportRecord.date(), medicalReportRecord.diagnosis(), medicalReportRecord.treatment(),
                medicalReportRecord.prescription(), medicalReportRecord.notes());
        log.debug("createMedicalRecord - End");
        return medicalRecordSaved;
    }

    @Override
    public void deleteMedicalRecord(Long medicalRecordId) {
        log.debug("deleteMedicalRecord - Start: Attempting to delete medical record with id [{}]", medicalRecordId);

        boolean medicalRecordExists = medicalReportRepository.existsById(medicalRecordId);
        if (!medicalRecordExists) {
            log.warn("deleteMedicalRecord - Not found: Medical record with id [{}] does not exist", medicalRecordId);
            throw new MedicalReportNotFoundException(format("Medical record with id %s not found", medicalRecordId), NOT_FOUND);
        }
        medicalReportRepository.deleteById(medicalRecordId);
        log.info("deleteMedicalRecord - Success: Medical record deleted with id [{}]", medicalRecordId);
        log.debug("deleteMedicalRecord - End");
    }

    @Override
    public Page<MedicalReportRecord> getAllMedicalRecords(Pageable pageable) {
        log.debug("getAllMedicalRecords - Start: Fetching medical records with page number [{}], page size [{}], sort: [{}]",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<MedicalReportRecord> MedicalReportRecordS = medicalReportRepository.findAll(pageable)
                .map(MedicalReport::toRecord);
        log.info("getAllMedicalRecords - Success: Retrieved medical records [{}] (page {} of {})",
                MedicalReportRecordS.getNumberOfElements(), MedicalReportRecordS.getNumber()+1, MedicalReportRecordS.getTotalPages() + 1);
        log.debug("getAllMedicalRecords - End");
        return MedicalReportRecordS;
    }

    @Override
    public MedicalReportRecord getMedicalRecord(Long medicalRecordId) {
        log.debug("getMedicalRecord - Start: Fetching medical record with id [{}]", medicalRecordId);
        MedicalReportRecord MedicalReportRecord = medicalReportRepository.findById(medicalRecordId)
                .map(MedicalReport::toRecord)
                .orElseThrow(() -> new MedicalReportNotFoundException(format("Medical record with id %s not found", medicalRecordId), NOT_FOUND));
        log.info("getMedicalRecord - Success: Retrieved medical record with id [{}]", medicalRecordId);
        log.debug("getMedicalRecord - End");
        return MedicalReportRecord;
    }

}
