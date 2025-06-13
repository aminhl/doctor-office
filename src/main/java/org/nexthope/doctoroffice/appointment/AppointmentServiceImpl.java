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

    @Override
    public AppointmentRecord createAppointment(AppointmentRecord appointmentRecord) {
        log.debug("createAppointment - Start: Attempting to create appointment [doctor: {}, patient: {}, startTime: {}, endTime: {}, status: {}, notes: {}]",
                String.format("%s %s", appointmentRecord.doctor().getFirstName(), appointmentRecord.doctor().getLastName()),
                String.format("%s %s", appointmentRecord.patient().getFirstName(), appointmentRecord.patient().getLastName()), appointmentRecord.startTime(),
                appointmentRecord.endTime(), appointmentRecord.status(), appointmentRecord.notes());
        boolean appointmentExists = appointmentRepository.existsByDoctorAndPatientAndStartTimeAndEndTime(appointmentRecord.doctor(), appointmentRecord.patient(),
                appointmentRecord.startTime(), appointmentRecord.endTime());
        if (appointmentExists) {
            log.warn("createAppointment - Aborted: Appointment already exists [doctor: {}, patient: {}, startTime: {}, endTime: {}, status: {}, notes: {}]",
                    String.format("%s %s", appointmentRecord.doctor().getFirstName(), appointmentRecord.doctor().getLastName()),
                    String.format("%s %s", appointmentRecord.patient().getFirstName(), appointmentRecord.patient().getLastName()), appointmentRecord.startTime(),
                    appointmentRecord.endTime(), appointmentRecord.status(), appointmentRecord.notes());
            throw new AppointmentAlreadyExistsException("Appointment already exists", CONFLICT);
        }
        var appointmentSaved = appointmentRepository.save(appointmentRecord.toAppointment()).toRecord();
        log.info("createAppointment - Success: Appointment created [doctor: {}, patient: {}, startTime: {}, endTime: {}, status: {}, notes: {}]",
                String.format("%s %s", appointmentRecord.doctor().getFirstName(), appointmentRecord.doctor().getLastName()),
                String.format("%s %s", appointmentRecord.patient().getFirstName(), appointmentRecord.patient().getLastName()), appointmentRecord.startTime(),
                appointmentRecord.endTime(), appointmentRecord.status(), appointmentRecord.notes());
        log.debug("createAppointment - End");
        return appointmentSaved;
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
    public Page<AppointmentRecord> getAllAppointments(Pageable pageable) {
        log.debug("getAllAppointments - Start: Fetching appointments with page number [{}], page size [{}], sort [{}]",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<AppointmentRecord> result = appointmentRepository.findAll(pageable)
                .map(Appointment::toRecord);
        log.info("getAllAppointments - Success: Retrieved appointments [{}] (page {} of {})",
                result.getNumberOfElements(), result.getNumber()+1, result.getTotalPages());
        log.debug("getAllAppointments - End:");
        return result;
    }

    @Override
    public AppointmentRecord getAppointment(Long appointmentId) {
        log.debug("getAppointment - Start: Fetching appointment with id [{}]", appointmentId);
        AppointmentRecord result = appointmentRepository.findById(appointmentId)
                .map(Appointment::toRecord)
                .orElseThrow(() -> new AppointmentNotFoundException(String.format("Appointment with id %s not found", appointmentId), NOT_FOUND));
        log.info("getAppointment - Success: Retrieved appointment with id [{}]", appointmentId);
        log.debug("getAppointment - End");
        return result;
    }
}
