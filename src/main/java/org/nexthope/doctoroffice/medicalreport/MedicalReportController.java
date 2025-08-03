package org.nexthope.doctoroffice.medicalreport;

import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResult;
import org.nexthope.doctoroffice.commons.PaginationRequest;
import org.nexthope.doctoroffice.commons.PagingResult;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.time.Instant.now;
import static org.nexthope.doctoroffice.medicalreport.MedicalReportConstants.MEDICAL_REPORTS_ENDPOINT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(MEDICAL_REPORTS_ENDPOINT)
@RequiredArgsConstructor
public class MedicalReportController {

    private final MedicalReportService medicalRecordService;

    @PostMapping
    public ResponseEntity<ApiResult<MedicalReportRecord>> createMedicalRecord(@RequestBody @Validated MedicalReportRecord medicalReportRecord) {
        MedicalReportRecord MedicalReportRecord = medicalRecordService.createMedicalReport(medicalReportRecord);
        ApiResult<MedicalReportRecord> apiResult = ApiResult.<MedicalReportRecord>builder()
                .success(true)
                .data(MedicalReportRecord)
                .statusCode(CREATED)
                .timestamp(now())
                .build();
        return ResponseEntity.status(CREATED).body(apiResult);
    }

    @DeleteMapping("/{medicalRecordId}")
    public ResponseEntity<ApiResult<Object>> deleteMedicalRecord(@PathVariable("medicalRecordId") Long medicalRecordId) {
        medicalRecordService.deleteMedicalReport(medicalRecordId);
        ApiResult<Object> apiResult = ApiResult.<Object>builder()
                .success(true)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping
    public ResponseEntity<ApiResult<PagingResult<MedicalReportRecord>>> findAllMedicalRecords(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) Sort.Direction direction
    ) {
        final PaginationRequest paginationRequest = PaginationRequest.of(page, size, sortField, direction);
        PagingResult<MedicalReportRecord> medicalRecordsDTOS = medicalRecordService.findAllMedicalReports(paginationRequest);
        ApiResult<PagingResult<MedicalReportRecord>> apiResult = ApiResult.<PagingResult<MedicalReportRecord>>builder()
                .success(true)
                .data(medicalRecordsDTOS)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{medicalRecordId}")
    public ResponseEntity<ApiResult<MedicalReportRecord>> findMedicalRecord(@PathVariable("medicalRecordId") Long medicalRecordId) {
        MedicalReportRecord MedicalReportRecord = medicalRecordService.findMedicalReport(medicalRecordId);
        ApiResult<MedicalReportRecord> apiResult = ApiResult.<MedicalReportRecord>builder()
                .success(true)
                .data(MedicalReportRecord)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResult);
    }

}
