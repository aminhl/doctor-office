package org.nexthope.doctoroffice.medicalreport;

import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResult;
import org.nexthope.doctoroffice.commons.pagination.PaginationRequest;
import org.nexthope.doctoroffice.commons.pagination.PagingResult;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.time.Instant.now;
import static org.nexthope.doctoroffice.medicalreport.MedicalReportConstants.MEDICAL_REPORTS_ENDPOINT;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(MEDICAL_REPORTS_ENDPOINT)
@RequiredArgsConstructor
public class MedicalReportController {

    private final MedicalReportService medicalRecordService;

    @PostMapping
    public ResponseEntity<ApiResult<MedicalReportDTO>> create(@RequestBody @Validated MedicalReportDTO medicalReportDTO) {
        MedicalReportDTO MedicalReportDTO = medicalRecordService.create(medicalReportDTO);
        ApiResult<MedicalReportDTO> apiResult = ApiResult.<MedicalReportDTO>builder()
                .success(true)
                .timestamp(now())
                .data(MedicalReportDTO)
                .build();
        return ResponseEntity.status(CREATED).body(apiResult);
    }

    @DeleteMapping("/{medicalRecordId}")
    public ResponseEntity<Void> delete(@PathVariable("medicalRecordId") Long medicalRecordId) {
        medicalRecordService.delete(medicalRecordId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ApiResult<PagingResult<MedicalReportDTO>>> findAll(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction direction,
            @RequestParam(defaultValue = "id", required = false) String sortField
    ) {
        final PaginationRequest paginationRequest = PaginationRequest.of(page, size, direction, sortField);
        PagingResult<MedicalReportDTO> medicalRecordsDTOS = medicalRecordService.findAll(paginationRequest);
        ApiResult<PagingResult<MedicalReportDTO>> apiResult = ApiResult.<PagingResult<MedicalReportDTO>>builder()
                .success(true)
                .timestamp(now())
                .data(medicalRecordsDTOS)
                .build();
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/{medicalRecordId}")
    public ResponseEntity<ApiResult<MedicalReportDTO>> find(@PathVariable("medicalRecordId") Long medicalRecordId) {
        MedicalReportDTO MedicalReportDTO = medicalRecordService.find(medicalRecordId);
        ApiResult<MedicalReportDTO> apiResult = ApiResult.<MedicalReportDTO>builder()
                .success(true)
                .timestamp(now())
                .data(MedicalReportDTO)
                .build();
        return ResponseEntity.ok(apiResult);
    }

}
