package org.nexthope.doctoroffice.medicalrecord;

import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.time.Instant.now;
import static org.nexthope.doctoroffice.commons.DoctorOfficeConstants.API_BASE_URL;
import static org.nexthope.doctoroffice.medicalrecord.MedicalRecordConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(API_BASE_URL + MEDICAL_RECORDS_ENDPOINT)
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<ApiResponse<MedicalRecordDTO>> createMedicalRecord(@RequestBody @Validated MedicalRecord medicalRecord) {
        MedicalRecordDTO medicalRecordDTO = medicalRecordService.createMedicalRecord(medicalRecord);
        ApiResponse<MedicalRecordDTO> apiResponse = ApiResponse.<MedicalRecordDTO>builder()
                .success(true)
                .message(MEDICAL_RECORD_CREATED_MESSAGE)
                .data(medicalRecordDTO)
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
                .message(MEDICAL_RECORD_DELETED_MESSAGE)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<MedicalRecordDTO>>> getAllMedicalRecords(Pageable pageable) {
        Page<MedicalRecordDTO> medicalRecordsDTOS = medicalRecordService.getAllMedicalRecords(pageable);
        ApiResponse<Page<MedicalRecordDTO>> apiResponse = ApiResponse.<Page<MedicalRecordDTO>>builder()
                .success(true)
                .message(MEDICAL_RECORDS_RETRIEVED_MESSAGE)
                .data(medicalRecordsDTOS)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{medicalRecordId}")
    public ResponseEntity<ApiResponse<MedicalRecordDTO>> getMedicalRecord(@PathVariable("medicalRecordId") Long medicalRecordId) {
        MedicalRecordDTO medicalRecordDTO = medicalRecordService.getMedicalRecord(medicalRecordId);
        ApiResponse<MedicalRecordDTO> apiResponse = ApiResponse.<MedicalRecordDTO>builder()
                .success(true)
                .message(MEDICAL_RECORD_RETRIEVED_MESSAGE)
                .data(medicalRecordDTO)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
