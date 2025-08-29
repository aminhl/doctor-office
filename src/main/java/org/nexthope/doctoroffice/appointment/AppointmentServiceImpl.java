package org.nexthope.doctoroffice.appointment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nexthope.doctoroffice.commons.pagination.PaginationRequest;
import org.nexthope.doctoroffice.commons.pagination.PaginationUtils;
import org.nexthope.doctoroffice.commons.pagination.PagingResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public AppointmentDTO create(AppointmentDTO appointmentDTO) {
        log.debug("createAppointment - Start: Attempting to create appointment");
        boolean appointmentExists = appointmentRepository.existsByDoctorAndPatientAndStartTimeAndEndTime(appointmentDTO.doctor(), appointmentDTO.patient(),
                appointmentDTO.startTime(), appointmentDTO.endTime());
        if (appointmentExists) {
            log.warn("createAppointment - Aborted: Appointment already exists");
            throw new AppointmentAlreadyExistsException("Appointment already exists", CONFLICT);
        }
        var appointmentSaved = appointmentRepository.save(appointmentDTO.toAppointment()).toRecord();
        log.info("createAppointment - Success: Appointment created");
        log.debug("createAppointment - End");
        return appointmentSaved;
    }

    @Override
    public void delete(Long appointmentId) {
        log.debug("deleteAppointment - Start : Attempting to delete appointment with id:{}", appointmentId);
        boolean appointmentExists = appointmentRepository.existsById(appointmentId);
        if (!appointmentExists) {
            log.warn("deleteAppointment - Not found: Appointment with id:{} does not exist", appointmentId);
            throw new AppointmentNotFoundException(String.format("Appointment with id %s not found", appointmentId), NOT_FOUND);
        }
        appointmentRepository.deleteById(appointmentId);
        log.info("deleteAppointment - Success: Appointment deleted with id:{}", appointmentId);
        log.debug("deleteAppointment - End");
    }

    @Override
    public PagingResult<AppointmentDTO> findAll(PaginationRequest paginationRequest) {
        final Pageable pageable = PaginationUtils.getPageable(paginationRequest);
        log.debug("getAllAppointments - Start: Fetching appointments with page_number:{}, page_size:{}, sort:{}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<AppointmentDTO> appointmentRecords = appointmentRepository.findAll(pageable)
                .map(Appointment::toRecord);
        log.info("getAllAppointments - Success: Retrieved {} appointment(s) (page {} of {})",
                appointmentRecords.getNumberOfElements(), appointmentRecords.getNumber()+1, appointmentRecords.getTotalPages());
        log.debug("getAllAppointments - End:");
        return new PagingResult<>(
                appointmentRecords.stream().toList(),
                appointmentRecords.getTotalPages(),
                appointmentRecords.getTotalElements(),
                appointmentRecords.getSize(),
                appointmentRecords.getNumber(),
                appointmentRecords.isEmpty()
        );
    }

    @Override
    public AppointmentDTO find(Long appointmentId) {
        log.debug("getAppointment - Start: Fetching appointment with id:{}", appointmentId);
        AppointmentDTO result = appointmentRepository.findById(appointmentId)
                .map(Appointment::toRecord)
                .orElseThrow(() -> new AppointmentNotFoundException(String.format("Appointment with id %s not found", appointmentId), NOT_FOUND));
        log.info("getAppointment - Success: Appointment with id:{} retrieved", appointmentId);
        log.debug("getAppointment - End");
        return result;
    }
}
