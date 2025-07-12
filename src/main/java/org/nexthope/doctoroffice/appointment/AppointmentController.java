package org.nexthope.doctoroffice.appointment;

import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResponse;
import org.nexthope.doctoroffice.commons.PaginationRequest;
import org.nexthope.doctoroffice.commons.PagingResult;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.time.Instant.now;
import static org.nexthope.doctoroffice.appointment.AppointmentConstants.APPOINTMENTS_ENDPOINT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(APPOINTMENTS_ENDPOINT)
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentRecord>> createAppointment(@RequestBody @Validated AppointmentRecord appointmentRecord) {
        var appointmentSaved = appointmentService.createAppointment(appointmentRecord);
        ApiResponse<AppointmentRecord> apiResponse = ApiResponse.<AppointmentRecord>builder()
                .success(true)
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
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagingResult<AppointmentRecord>>> findAllAppointments(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) Sort.Direction direction
    ) {
        final PaginationRequest paginationRequest = PaginationRequest.of(page, size, sortField, direction);
        PagingResult<AppointmentRecord> appointmentRecords = appointmentService.findAllAppointments(paginationRequest);
        ApiResponse<PagingResult<AppointmentRecord>> apiResponse = ApiResponse.<PagingResult<AppointmentRecord>>builder()
                .success(true)
                .statusCode(OK)
                .data(appointmentRecords)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<AppointmentRecord>> findAppointment(@PathVariable("appointmentId") Long appointmentId) {
        AppointmentRecord appointmentRecord = appointmentService.findAppointment(appointmentId);
        ApiResponse<AppointmentRecord> apiResponse = ApiResponse.<AppointmentRecord>builder()
                .success(true)
                .data(appointmentRecord)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

}
