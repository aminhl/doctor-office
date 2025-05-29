package org.nexthope.doctoroffice.clinic;

import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.time.Instant.now;
import static org.nexthope.doctoroffice.clinic.ClinicConstants.*;
import static org.nexthope.doctoroffice.commons.DoctorOfficeConstants.API_BASE_URL;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(API_BASE_URL + CLINICS_ENDPOINT)
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @PostMapping
    public ResponseEntity<ApiResponse<ClinicDTO>> createClinic(@RequestBody @Validated Clinic clinic) {
        ClinicDTO clinicDTO = clinicService.createClinic(clinic);
        ApiResponse<ClinicDTO> apiResponse = ApiResponse.<ClinicDTO>builder()
                .success(true)
                .message(CLINIC_CREATED_MESSAGE)
                .data(clinicDTO)
                .statusCode(HttpStatus.CREATED)
                .timestamp(now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @DeleteMapping("/{clinicId}")
    public ResponseEntity<ApiResponse<Object>> deleteClinic(@PathVariable Long clinicId) {
        clinicService.deleteClinic(clinicId);
        ApiResponse<Object> apiResponse = ApiResponse.<Object>builder()
                .success(true)
                .message(CLINIC_DELETED_MESSAGE)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ClinicDTO>>> getAllClinics(Pageable pageable) {
        Page<ClinicDTO> page = clinicService.getAllClinics(pageable);
        ApiResponse<Page<ClinicDTO>> response = ApiResponse.<Page<ClinicDTO>>builder()
                .success(true)
                .message(CLINICS_RETRIEVED_MESSAGE)
                .statusCode(OK)
                .data(page)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{clinicId}")
    public ResponseEntity<ApiResponse<ClinicDTO>> getClinic(@PathVariable Long clinicId) {
        ClinicDTO clinic = clinicService.getClinic(clinicId);
        ApiResponse<ClinicDTO> response = ApiResponse.<ClinicDTO>builder()
                .success(true)
                .message(CLINIC_RETRIEVED_MESSAGE)
                .statusCode(OK)
                .data(clinic)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(response);
    }

}
