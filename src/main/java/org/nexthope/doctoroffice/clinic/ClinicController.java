package org.nexthope.doctoroffice.clinic;

import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResult;
import org.nexthope.doctoroffice.commons.pagination.PaginationRequest;
import org.nexthope.doctoroffice.commons.pagination.PagingResult;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.time.Instant.now;
import static org.nexthope.doctoroffice.clinic.ClinicConstants.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(CLINICS_ENDPOINT)
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @PostMapping
    public ResponseEntity<ApiResult<ClinicDTO>> create(@RequestBody @Validated ClinicDTO clinicDTO) {
        var clinicSaved = clinicService.create(clinicDTO);
        ApiResult<ClinicDTO> apiResult = ApiResult.<ClinicDTO>builder()
                .success(true)
                .timestamp(now())
                .data(clinicSaved)
                .build();
        return ResponseEntity.status(CREATED).body(apiResult);
    }

    @DeleteMapping("/{clinicId}")
    public ResponseEntity<Void> delete(@PathVariable Long clinicId) {
        clinicService.delete(clinicId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ApiResult<PagingResult<ClinicDTO>>> findAll(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(defaultValue = "DESC", required = false) Direction direction,
            @RequestParam(defaultValue = "id", required = false) String sortField
            ) {
        final PaginationRequest paginationRequest = PaginationRequest.of(page, size, direction, sortField);
        PagingResult<ClinicDTO> clinicRecords = clinicService.findAll(paginationRequest);
        ApiResult<PagingResult<ClinicDTO>> response = ApiResult.<PagingResult<ClinicDTO>>builder()
                .success(true)
                .timestamp(now())
                .data(clinicRecords)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{clinicId}")
    public ResponseEntity<ApiResult<ClinicDTO>> find(@PathVariable("clinicId") Long clinicId) {
        ClinicDTO clinic = clinicService.find(clinicId);
        ApiResult<ClinicDTO> response = ApiResult.<ClinicDTO>builder()
                .success(true)
                .timestamp(now())
                .data(clinic)
                .build();
        return ResponseEntity.ok(response);
    }

}
