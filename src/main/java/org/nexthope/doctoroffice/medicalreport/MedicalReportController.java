package org.nexthope.doctoroffice.medicalreport;

import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.time.Instant.now;
import static org.nexthope.doctoroffice.commons.DoctorOfficeConstants.API_V1_PATH;
import static org.nexthope.doctoroffice.medicalreport.MedicalReportConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(API_V1_PATH + MEDICAL_REPORTS_ENDPOINT)
@RequiredArgsConstructor
public class MedicalReportController {

    private final MedicalReportService medicalRecordService;

    @PostMapping
    public ResponseEntity<ApiResponse<MedicalReportRecord>> createMedicalRecord(@RequestBody @Validated MedicalReportRecord medicalReportRecord) {
        MedicalReportRecord MedicalReportRecord = medicalRecordService.createMedicalRecord(medicalReportRecord);
        ApiResponse<MedicalReportRecord> apiResponse = ApiResponse.<MedicalReportRecord>builder()
                .success(true)
                .message(MEDICAL_REPORT_CREATED_MESSAGE)
                .data(MedicalReportRecord)
                .statusCode(CREATED)
                .timestamp(now())
                .build();
        return ResponseEntity.status(CREATED).body(apiResponse);
    }

    @DeleteMapping("/{medicalRecordId}")
    public ResponseEntity<ApiResponse<Object>> deleteMedicalRecord(@PathVariable("medicalRecordId") Long medicalRecordId) {
        medicalRecordService.deleteMedicalRecord(medicalRecordId);
        ApiResponse<Object> apiResponse = ApiResponse.<Object>builder()
                .success(true)
                .message(MEDICAL_REPORT_DELETED_MESSAGE)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<MedicalReportRecord>>> getAllMedicalRecords(Pageable pageable) {
        Page<MedicalReportRecord> medicalRecordsDTOS = medicalRecordService.getAllMedicalRecords(pageable);
        ApiResponse<Page<MedicalReportRecord>> apiResponse = ApiResponse.<Page<MedicalReportRecord>>builder()
                .success(true)
                .message(MEDICAL_REPORTS_RETRIEVED_MESSAGE)
                .data(medicalRecordsDTOS)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{medicalRecordId}")
    public ResponseEntity<ApiResponse<MedicalReportRecord>> getMedicalRecord(@PathVariable("medicalRecordId") Long medicalRecordId) {
        MedicalReportRecord MedicalReportRecord = medicalRecordService.getMedicalRecord(medicalRecordId);
        ApiResponse<MedicalReportRecord> apiResponse = ApiResponse.<MedicalReportRecord>builder()
                .success(true)
                .message(MEDICAL_REPORT_RETRIEVED_MESSAGE)
                .data(MedicalReportRecord)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
