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
import static org.nexthope.doctoroffice.commons.DoctorOfficeConstants.API_BASE_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(API_BASE_URL + APPOINTMENTS_ENDPOINT)
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentDTO>> createAppointment(@RequestBody @Validated Appointment appointment) {
        AppointmentDTO appointmentDTO = appointmentService.createAppointment(appointment);
        ApiResponse<AppointmentDTO> apiResponse = ApiResponse.<AppointmentDTO>builder()
                .success(true)
                .message(APPOINTMENT_CREATED_MESSAGE)
                .data(appointmentDTO)
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
    public ResponseEntity<ApiResponse<Page<AppointmentDTO>>> getAllAppointments(Pageable pageable) {
        Page<AppointmentDTO> appointmentDTOS = appointmentService.getAllAppointments(pageable);
        ApiResponse<Page<AppointmentDTO>> apiResponse = ApiResponse.<Page<AppointmentDTO>>builder()
                .success(true)
                .message(APPOINTMENTS_RETRIEVED_MESSAGE)
                .data(appointmentDTOS)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<AppointmentDTO>> getAppointment(@PathVariable("appointmentId") Long appointmentId) {
        AppointmentDTO appointmentDTO = appointmentService.getAppointment(appointmentId);
        ApiResponse<AppointmentDTO> apiResponse = ApiResponse.<AppointmentDTO>builder()
                .success(true)
                .message(APPOINTMENT_RETRIEVED_MESSAGE)
                .data(appointmentDTO)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
