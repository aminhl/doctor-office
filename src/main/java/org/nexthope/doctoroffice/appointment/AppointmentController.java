package org.nexthope.doctoroffice.appointment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResult;
import io.swagger.v3.oas.annotations.responses.*;
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
@Tag(name = "Appointments", description = "Operations pertaining to appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @Operation(
            summary = "Create a new appointment",
            description = "Creates a new appointment and returns the created record"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Appointment successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized – authentication is required"),
            @ApiResponse(responseCode = "403", description = "Forbidden – insufficient permissions"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResult<AppointmentRecord>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Appointment data to be created",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AppointmentRecord.class)
                    )
            )
            @RequestBody @Validated AppointmentRecord appointmentRecord
    ) {
        var appointmentSaved = appointmentService.create(appointmentRecord);
        ApiResult<AppointmentRecord> apiResult = ApiResult.<AppointmentRecord>builder()
                .success(true)
                .data(appointmentSaved)
                .statusCode(CREATED)
                .timestamp(now())
                .build();
        return ResponseEntity.status(CREATED).body(apiResult);
    }

    @Operation(
            summary = "Delete an appointment",
            description = "Deletes the appointment with the given ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment successfully deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized – authentication is required"),
            @ApiResponse(responseCode = "403", description = "Forbidden – insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Appointment not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @DeleteMapping(path = "/{appointmentId}")
    public ResponseEntity<ApiResult<Object>> delete(
            @Parameter(description = "ID of the appointment to delete", required = true, example = "123")
            @PathVariable("appointmentId") Long appointmentId) {
        appointmentService.delete(appointmentId);
        ApiResult<Object> apiResult = ApiResult.<Object>builder()
                .success(true)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping
    @Operation(
            summary = "Get all appointments",
            description = "Returns a paginated list of appointments"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Appointments successfully retrieved"),
        @ApiResponse(responseCode = "401", description = "Unauthorized – authentication is required"),
        @ApiResponse(responseCode = "403", description = "Forbidden – insufficient permissions"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "500", description = "Unexpected server error")
    }
    )
    public ResponseEntity<ApiResult<PagingResult<AppointmentRecord>>> findAll(
            @Parameter(description = "Page number (starting from 0)", example = "0")
            @RequestParam(required = false) Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(required = false) Integer size,
            @Parameter(description = "Sort field", example = "id")
            @RequestParam(required = false) String sortField,
            @Parameter(description = "Sort direction", example = "ASC")
            @RequestParam(required = false) Sort.Direction direction
    ) {
        final PaginationRequest paginationRequest = PaginationRequest.of(page, size, sortField, direction);
        PagingResult<AppointmentRecord> appointmentRecords = appointmentService.findAll(paginationRequest);
        ApiResult<PagingResult<AppointmentRecord>> apiResult = ApiResult.<PagingResult<AppointmentRecord>>builder()
                .success(true)
                .statusCode(OK)
                .data(appointmentRecords)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResult);
    }

    @Operation(
            summary = "Get an appointment by ID",
            description = "Returns the appointment with the given ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized – authentication is required"),
            @ApiResponse(responseCode = "403", description = "Forbidden – insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Appointment not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/{appointmentId}")
    public ResponseEntity<ApiResult<AppointmentRecord>> find(
            @Parameter(description = "ID of the appointment to retrieve", required = true, example = "123")
            @PathVariable("appointmentId") Long appointmentId
    ) {
        AppointmentRecord appointmentRecord = appointmentService.find(appointmentId);
        ApiResult<AppointmentRecord> apiResult = ApiResult.<AppointmentRecord>builder()
                .success(true)
                .data(appointmentRecord)
                .statusCode(OK)
                .timestamp(now())
                .build();
        return ResponseEntity.ok(apiResult);
    }

}
