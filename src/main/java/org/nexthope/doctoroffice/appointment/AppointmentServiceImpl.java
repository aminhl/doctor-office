package org.nexthope.doctoroffice.appointment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final AppointmentMapper appointmentMapper;

    @Override
    public AppointmentDTO createAppointment(Appointment appointment) {
        log.debug("createAppointment - Start: Attempting to create appointment [doctor: {}, patient: {}, startTime: {}, endTime: {}, status: {}, notes: {}]",
                String.format("%s %s", appointment.getDoctor().getFirstName(), appointment.getDoctor().getLastName()),
                String.format("%s %s", appointment.getPatient().getFirstName(), appointment.getPatient().getLastName()), appointment.getStartTime(), appointment.getEndTime(), appointment.getStatus(), appointment.getNotes());
        boolean appointmentExists = appointmentRepository.existsByDoctorAndPatientAndStartTimeAndEndTime(appointment.getDoctor(), appointment.getPatient(),
                appointment.getStartTime(), appointment.getEndTime());
        if (appointmentExists) {
            log.warn("createAppointment - Aborted: Appointment already exists [doctor: {}, patient: {}, startTime: {}, endTime: {}, status: {}, notes: {}]",
                    String.format("%s %s", appointment.getDoctor().getFirstName(), appointment.getDoctor().getLastName()),
                    String.format("%s %s", appointment.getPatient().getFirstName(), appointment.getPatient().getLastName()), appointment.getStartTime(), appointment.getEndTime(), appointment.getStatus(), appointment.getNotes());
            throw new AppointmentAlreadyExistsException("Appointment already exists", CONFLICT);
        }
        var appointmentSaved = appointmentRepository.save(appointment);
        AppointmentDTO appointmentDTO = appointmentMapper.toDto(appointmentSaved);
        log.info("createAppointment - Success: Appointment created [doctor: {}, patient: {}, startTime: {}, endTime: {}, status: {}, notes: {}]",
                String.format("%s %s", appointment.getDoctor().getFirstName(), appointment.getDoctor().getLastName()),
                String.format("%s %s", appointment.getPatient().getFirstName(), appointment.getPatient().getLastName()), appointment.getStartTime(), appointment.getEndTime(), appointment.getStatus(), appointment.getNotes());
        log.debug("createAppointment - End");
        return appointmentDTO;
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        log.debug("deleteAppointment - Start : Attempting to delete appointment with id [{}]", appointmentId);
        boolean appointmentExists = appointmentRepository.existsById(appointmentId);
        if (!appointmentExists) {
            log.warn("deleteAppointment - Not found: Appointment with id [{}] does not exist", appointmentId);
            throw new AppointmentNotFoundException(String.format("Appointment with id %s not found", appointmentId), NOT_FOUND);
        }
        appointmentRepository.deleteById(appointmentId);
        log.info("deleteAppointment - Success: Appointment deleted with id [{}]", appointmentId);
        log.debug("deleteAppointment - End");
    }

    @Override
    public Page<AppointmentDTO> getAllAppointments(Pageable pageable) {
        log.debug("getAllAppointments - Start: Fetching appointments with page number [{}], page size [{}], sort [{}]",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<AppointmentDTO> result = appointmentRepository.findAll(pageable)
                        .map(appointmentMapper::toDto);
        log.info("getAllAppointments - Success: Retrieved appointments [{}] (page {} of {})",
                result.getNumberOfElements(), result.getNumber(), result.getTotalPages());
        log.debug("getAllAppointments - End:");
        return null;
    }

    @Override
    public AppointmentDTO getAppointment(Long appointmentId) {
        log.debug("getAppointment - Start: Fetching appointment with id [{}]", appointmentId);
        AppointmentDTO result = appointmentRepository.findById(appointmentId)
                        .map(appointmentMapper::toDto)
                        .orElseThrow(() -> new AppointmentNotFoundException(String.format("Appointment with id %s not found", appointmentId), NOT_FOUND));
        log.info("getAppointment - Success: Retrieved appointment with id [{}]", appointmentId);
        log.debug("getAppointment - End");
        return result;
    }
}
