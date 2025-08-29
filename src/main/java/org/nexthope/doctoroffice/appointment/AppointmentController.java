package org.nexthope.doctoroffice.appointment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.nexthope.doctoroffice.commons.ApiResult;
import org.nexthope.doctoroffice.commons.pagination.PaginationRequest;
import org.nexthope.doctoroffice.commons.pagination.PagingResult;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static java.time.Instant.now;
import static org.nexthope.doctoroffice.appointment.AppointmentConstants.APPOINTMENTS_ENDPOINT;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(APPOINTMENTS_ENDPOINT)
@RequiredArgsConstructor
@Tag(name = "Appointments", description = "Operations pertaining to appointments")
@Validated
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @Operation(
            summary = "Create a new appointment",
            description = "Creates a new appointment and returns the created record"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Appointment successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content()),
            @ApiResponse(responseCode = "401", description = "Unauthorized – authentication is required", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden – insufficient permissions", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    public ResponseEntity<ApiResult<AppointmentDTO>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Appointment data to be created",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AppointmentDTO.class)
                    )
            )
            @RequestBody @Validated AppointmentDTO appointmentDTO
    ) {
        var appointmentSaved = appointmentService.create(appointmentDTO);
        ApiResult<AppointmentDTO> apiResult = ApiResult.<AppointmentDTO>builder()
                .success(true)
                .timestamp(now())
                .data(appointmentSaved)
                .build();
        return ResponseEntity.status(CREATED).body(apiResult);
    }

    @Operation(
            summary = "Delete an appointment",
            description = "Deletes the appointment with the given ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Appointment successfully deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized – authentication is required", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden – insufficient permissions", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content())
    })
    @DeleteMapping(path = "/{appointmentId}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the appointment to delete", required = true, example = "123")
            @PathVariable("appointmentId") Long appointmentId) {
        appointmentService.delete(appointmentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(
            summary = "Get all appointments",
            description = "Returns a paginated list of appointments"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Appointments successfully retrieved"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content()),
        @ApiResponse(responseCode = "401", description = "Unauthorized – authentication is required", content = @Content()),
        @ApiResponse(responseCode = "403", description = "Forbidden – insufficient permissions", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content())
    }
    )
    public ResponseEntity<ApiResult<PagingResult<AppointmentDTO>>> findAll(
            @Parameter(description = "Page number (starting from 0)", example = "0")
            @RequestParam(defaultValue = "0", required = false) @Min(0) Integer page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10", required = false) @Positive Integer size,
            @Parameter(description = "Sort direction", example = "ASC")
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction direction,
            @Parameter(description = "Sort field", example = "id")
            @RequestParam(defaultValue = "id", required = false) String sortField
    ) {
        final PaginationRequest paginationRequest = PaginationRequest.of(page, size, direction, sortField);
        PagingResult<AppointmentDTO> appointmentRecords = appointmentService.findAll(paginationRequest);
        ApiResult<PagingResult<AppointmentDTO>> apiResult = ApiResult.<PagingResult<AppointmentDTO>>builder()
                .success(true)
                .timestamp(now())
                .data(appointmentRecords)
                .build();
        return ResponseEntity.ok(apiResult);
    }

    @Operation(
            summary = "Get an appointment by ID",
            description = "Returns the appointment with the given ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized – authentication is required", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden – insufficient permissions", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content())
    })
    @GetMapping("/{appointmentId}")
    public ResponseEntity<ApiResult<AppointmentDTO>> find(
            @Parameter(description = "ID of the appointment to retrieve", required = true, example = "123")
            @PathVariable("appointmentId") Long appointmentId
    ) {
        AppointmentDTO appointmentDTO = appointmentService.find(appointmentId);
        ApiResult<AppointmentDTO> apiResult = ApiResult.<AppointmentDTO>builder()
                .success(true)
                .timestamp(now())
                .data(appointmentDTO)
                .build();
        return ResponseEntity.ok(apiResult);
    }

}
