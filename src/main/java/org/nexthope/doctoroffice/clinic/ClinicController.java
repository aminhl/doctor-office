package org.nexthope.doctoroffice.clinic;

import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResult;
import org.nexthope.doctoroffice.commons.PaginationRequest;
import org.nexthope.doctoroffice.commons.PagingResult;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.time.Instant.now;
import static org.nexthope.doctoroffice.clinic.ClinicConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(CLINICS_ENDPOINT)
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @PostMapping
    public ResponseEntity<ApiResult<ClinicRecord>> createClinic(@RequestBody @Validated ClinicRecord clinicRecord) {
        var clinicSaved = clinicService.createClinic(clinicRecord);
        ApiResult<ClinicRecord> apiResult = ApiResult.<ClinicRecord>builder()
                .success(true)
                .data(clinicSaved)
                .statusCode(CREATED)
                .timestamp(now())
                .build();
        return ResponseEntity.status(CREATED).body(apiResult);
    }

    @DeleteMapping("/{clinicId}")
    public ResponseEntity<ApiResult<Object>> deleteClinic(@PathVariable Long clinicId) {
        clinicService.deleteClinic(clinicId);
        ApiResult<Object> apiResult = ApiResult.<Object>builder()
                .success(true)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping
    public ResponseEntity<ApiResult<PagingResult<ClinicRecord>>> findAllClinics(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) Direction direction
            ) {
        final PaginationRequest paginationRequest = PaginationRequest.of(page, size, sortField, direction);
        PagingResult<ClinicRecord> clinicRecords = clinicService.findAllClinics(paginationRequest);
        ApiResult<PagingResult<ClinicRecord>> response = ApiResult.<PagingResult<ClinicRecord>>builder()
                .success(true)
                .statusCode(OK)
                .data(clinicRecords)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{clinicId}")
    public ResponseEntity<ApiResult<ClinicRecord>> findClinic(@PathVariable("clinicId") Long clinicId) {
        ClinicRecord clinic = clinicService.findClinic(clinicId);
        ApiResult<ClinicRecord> response = ApiResult.<ClinicRecord>builder()
                .success(true)
                .statusCode(OK)
                .data(clinic)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(response);
    }

}
