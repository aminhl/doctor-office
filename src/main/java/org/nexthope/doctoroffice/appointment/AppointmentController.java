package org.nexthope.doctoroffice.appointment;

import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.time.Instant.now;
import static org.nexthope.doctoroffice.appointment.AppointmentConstants.*;
import static org.nexthope.doctoroffice.commons.DoctorOfficeConstants.API_V1_PATH;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(API_V1_PATH + APPOINTMENTS_ENDPOINT)
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentRecord>> createAppointment(@RequestBody @Validated AppointmentRecord appointmentRecord) {
        var appointmentSaved = appointmentService.createAppointment(appointmentRecord);
        ApiResponse<AppointmentRecord> apiResponse = ApiResponse.<AppointmentRecord>builder()
                .success(true)
                .message(APPOINTMENT_CREATED_MESSAGE)
                .data(appointmentSaved)
                .statusCode(CREATED)
                .timestamp(now())
                .build();
        return ResponseEntity.status(CREATED).body(apiResponse);
    }

    @DeleteMapping(path = "/{appointmentId}")
    public ResponseEntity<ApiResponse<Object>> deleteAppointment(@PathVariable("appointmentId") Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        ApiResponse<Object> apiResponse = ApiResponse.<Object>builder()
                .success(true)
                .message(APPOINTMENT_DELETED_MESSAGE)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AppointmentRecord>>> getAllAppointments(Pageable pageable) {
        Page<AppointmentRecord> page = appointmentService.getAllAppointments(pageable);
        ApiResponse<Page<AppointmentRecord>> apiResponse = ApiResponse.<Page<AppointmentRecord>>builder()
                .success(true)
                .message(APPOINTMENTS_RETRIEVED_MESSAGE)
                .statusCode(OK)
                .data(page)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<AppointmentRecord>> getAppointment(@PathVariable("appointmentId") Long appointmentId) {
        AppointmentRecord appointmentRecord = appointmentService.getAppointment(appointmentId);
        ApiResponse<AppointmentRecord> apiResponse = ApiResponse.<AppointmentRecord>builder()
                .success(true)
                .message(APPOINTMENT_RETRIEVED_MESSAGE)
                .data(appointmentRecord)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
