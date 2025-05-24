package org.nexthope.doctoroffice.clinic;

import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResponse;
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

    @DeleteMapping(value = "/{clinicId}")
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

}
