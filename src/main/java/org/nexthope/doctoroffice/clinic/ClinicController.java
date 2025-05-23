package org.nexthope.doctoroffice.clinic;

import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

import static org.nexthope.doctoroffice.clinic.ClinicConstants.*;
import static org.nexthope.doctoroffice.commons.DoctorOfficeConstants.API_BASE_URL;

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
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


}
