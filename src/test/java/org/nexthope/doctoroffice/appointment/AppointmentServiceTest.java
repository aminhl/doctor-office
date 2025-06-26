package org.nexthope.doctoroffice.appointment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nexthope.doctoroffice.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.nexthope.doctoroffice.appointment.Status.CANCELED;
import static org.nexthope.doctoroffice.appointment.Status.SCHEDULED;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Test
    void testCreateAppointment_whenAppointmentDoesNotExist_shouldCreateAndReturnAppointmentRecord() {
        // Given
        AppointmentRecord appointmentRecord = new AppointmentRecord(
                1L,
                new User(),
                new User(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                "notes",
                SCHEDULED,
                LocalDateTime.now(),
                null
        );
        Appointment appointment = appointmentRecord.toAppointment();
        when(appointmentRepository.existsByDoctorAndPatientAndStartTimeAndEndTime(appointmentRecord.doctor(), appointmentRecord.patient(), appointmentRecord.startTime(), appointmentRecord.endTime()))
                .thenReturn(false);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        // When
        var result = appointmentService.createAppointment(appointmentRecord);
        // Then
        assertNotNull(result);
        assertEquals(result, appointmentRecord);
        verify(appointmentRepository).save(appointment);
    }

    @Test
    void testCreateAppointment_whenAppointmentAlreadyExists_shouldThrowAppointmentAlreadyExistsException() {
        // Given
        AppointmentRecord appointmentRecord = new AppointmentRecord(
                1L,
                new User(),
                new User(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                "notes",
                SCHEDULED,
                LocalDateTime.now(),
                null
        );
        Appointment appointment = appointmentRecord.toAppointment();
        when(appointmentRepository.existsByDoctorAndPatientAndStartTimeAndEndTime(appointmentRecord.doctor(), appointmentRecord.patient(), appointmentRecord.startTime(), appointmentRecord.endTime()))
                .thenReturn(true);
        // When
        AppointmentAlreadyExistsException exception = assertThrows(AppointmentAlreadyExistsException.class, () -> appointmentService.createAppointment(appointmentRecord));
        // Then
        verify(appointmentRepository, never()).save(appointment);
        assertEquals(CONFLICT, exception.getErrorCode());
        assertEquals("Appointment already exists", exception.getMessage());
    }

    @Test
    void testDeleteAppointment_whenAppointmentExists_shouldDeleteAppointment() {
        // Given
        Long appointmentId = 1L;
        when(appointmentRepository.existsById(appointmentId)).thenReturn(true);
        // When
        appointmentService.deleteAppointment(appointmentId);
        // Then
        verify(appointmentRepository).deleteById(appointmentId);
    }

    @Test
    void testDeleteAppointment_whenAppointmentDoesNotExists_shouldThrowAppointmentNotFoundException() {
        // Given
        Long appointmentId = 1L;
        when(appointmentRepository.existsById(appointmentId)).thenReturn(false);
        // When
        AppointmentNotFoundException exception = assertThrows(AppointmentNotFoundException.class, () -> appointmentService.deleteAppointment(appointmentId));
        // Then
        verify(appointmentRepository, never()).deleteById(appointmentId);
        assertEquals(NOT_FOUND, exception.getErrorCode());
        assertEquals("Appointment with id " + appointmentId + " not found", exception.getMessage());
    }

    @Test
    void testGetAllAppointments_withNoEmptyAppointments_shouldReturnNoEmptyPageOfAppointmentRecord() {
        // Given
        Appointment appointment = new Appointment()
                .setId(1L)
                .setDoctor(new User())
                .setPatient(new User())
                .setStartTime(LocalDateTime.now())
                .setEndTime(LocalDateTime.now().plusMinutes(30))
                .setNotes("notes")
                .setStatus(SCHEDULED)
                .setCreationDate(LocalDateTime.now())
                .setModificationDate(null);
        Appointment appointment1 = new Appointment()
                .setId(2L)
                .setDoctor(new User())
                .setPatient(new User())
                .setStartTime(LocalDateTime.now().plusMinutes(10))
                .setEndTime(LocalDateTime.now().plusMinutes(40))
                .setNotes("notes-1")
                .setStatus(CANCELED)
                .setCreationDate(LocalDateTime.now())
                .setModificationDate(null);
        List<Appointment> appointments = List.of(appointment, appointment1);
        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<Appointment> appointmentsPage = new PageImpl<>(appointments, pageRequest, appointments.size());
        when(appointmentRepository.findAll(pageRequest)).thenReturn(appointmentsPage);
        // When
        Page<AppointmentRecord> result = appointmentService.getAllAppointments(pageRequest);
        // Then
        assertNotNull(result);
        assertEquals(result.getContent().size(), appointments.size());
        assertEquals(result.getContent().get(0).id(), appointment.getId());
        assertEquals(result.getContent().get(1).id(), appointment1.getId());
    }

    @Test
    void testGetAllAppointments_withEmptyAppointments_shouldReturnEmptyPageOfAppointmentRecord() {
        // Given
        List<Appointment> appointments = Collections.emptyList();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Appointment> appointmentPage = new PageImpl<>(appointments, pageRequest, appointments.size());
        when(appointmentRepository.findAll(pageRequest)).thenReturn(appointmentPage);
        // When
        Page<AppointmentRecord> result = appointmentService.getAllAppointments(pageRequest);
        // Then
        assertNotNull(result);
        assertEquals(result.getContent().size(), appointments.size());
    }

    @Test
    void testGetAppointment_withExistingAppointment_shouldReturnAppointmentRecord() {
        // Given
        Long appointmentId = 1L;
        Appointment appointment = new Appointment()
                .setId(appointmentId)
                .setDoctor(new User())
                .setPatient(new User())
                .setStartTime(LocalDateTime.now())
                .setEndTime(LocalDateTime.now().plusMinutes(30))
                .setNotes("notes")
                .setStatus(SCHEDULED)
                .setCreationDate(LocalDateTime.now())
                .setModificationDate(null);
        AppointmentRecord appointmentRecord = appointment.toRecord();
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        // When
        AppointmentRecord result = appointmentService.getAppointment(appointmentId);
        // Then
        assertNotNull(result);
        assertEquals(appointmentRecord, result);
    }

    @Test
    void testGetAppointment_withNoExistingAppointment_shouldThrowAppointmentNotFoundException() {
        // Given
        Long appointmentId = 1L;
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());
        // When
        AppointmentNotFoundException exception = assertThrows(AppointmentNotFoundException.class, () -> appointmentService.getAppointment(appointmentId));
        // Then
        assertEquals(NOT_FOUND, exception.getErrorCode());
        assertEquals("Appointment with id " + appointmentId + " not found", exception.getMessage());
    }

}
