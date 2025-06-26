package org.nexthope.doctoroffice.clinic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {

    @Mock
    private ClinicRepository clinicRepository;

    @InjectMocks
    private ClinicServiceImpl clinicService;

    @Test
    void testCreateClinic_withValidClinic_shouldReturnClinicRecord() {
        // Given
        ClinicRecord clinicRecord = new Clinic()
                .setId(1L)
                .setName("clinic_1")
                .setAddress("address_1")
                .setCity("city_1")
                .setPhoneNumber("0123456789")
                .setCreationDate(now())
                .toRecord();
        Clinic clinic = clinicRecord.toClinic();
        when(clinicRepository.existsByNameAndAddressAndCity(clinicRecord.name(), clinicRecord.address(), clinicRecord.city()))
                .thenReturn(false);
        when(clinicRepository.save(clinic)).thenReturn(clinic);
        // When
        ClinicRecord result = clinicService.createClinic(clinicRecord);
        // Then
        assertNotNull(result);
        verify(clinicRepository).save(clinic);
        assertEquals(clinicRecord, result);
    }

    @Test
    void testCreateClinic_withExistingClinic_shouldThrowClinicAlreadyExistsException() {
        // Given
        ClinicRecord clinicRecord = new Clinic()
                .setId(1L)
                .setName("clinic_1")
                .setAddress("address_1")
                .setCity("city_1")
                .setPhoneNumber("0123456789")
                .setCreationDate(now())
                .toRecord();
        when(clinicRepository.existsByNameAndAddressAndCity(clinicRecord.name(), clinicRecord.address(), clinicRecord.city()))
                .thenReturn(true);
        // When
        ClinicAlreadyExistsException exception = assertThrows(ClinicAlreadyExistsException.class, () -> clinicService.createClinic(clinicRecord));
        // Then
        assertEquals("Clinic " + clinicRecord.name() + " already exists", exception.getMessage());
        assertEquals(CONFLICT, exception.getErrorCode());
        verify(clinicRepository, never()).save(clinicRecord.toClinic());
    }

    @Test
    void testDeleteClinic_withNoExistingClinic_shouldThrowClinicNotFoundException() {
        // Given
        Long clinicId = 1L;
        when(clinicRepository.existsById(clinicId)).thenReturn(false);
        // When
        ClinicNotFoundException exception = assertThrows(ClinicNotFoundException.class, () -> clinicService.deleteClinic(clinicId));
        // Then
        assertEquals("Clinic with id " + clinicId + " not found", exception.getMessage());
        assertEquals(NOT_FOUND, exception.getErrorCode());
        verify(clinicRepository, never()).deleteById(clinicId);
    }

    @Test
    void testDeleteClinic_withExistingClinic_shouldDeleteClinic() {
        // Given
        Long clinicId = 1L;
        when(clinicRepository.existsById(clinicId)).thenReturn(true);
        // When
        clinicService.deleteClinic(clinicId);
        // Then
        verify(clinicRepository).deleteById(clinicId);
    }

    @Test
    void testGetAllClinics_withNoEmptyClinics_shouldReturnNoEmptyPageOfClinicRecord() {
        Clinic clinic1 = new Clinic();
        Clinic clinic2 = new Clinic();
        clinic1.setId(1L)
                .setName("clinic_1")
                .setAddress("address_1")
                .setCity("city_1")
                .setPhoneNumber("0123456789")
                .setCreationDate(now());
        clinic2.setId(2L)
                .setName("clinic_2")
                .setAddress("address_2")
                .setCity("city_2")
                .setPhoneNumber("0123456789")
                .setCreationDate(now());
        List<Clinic> clinics = List.of(clinic1, clinic2);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Clinic> clinicsPage = new PageImpl<>(clinics, pageRequest, clinics.size());
        when(clinicRepository.findAll(pageRequest)).thenReturn(clinicsPage);
        // When
        Page<ClinicRecord> result = clinicService.getAllClinics(pageRequest);
        // Then
        assertNotNull(result);
        assertThat(result.getContent().size()).isEqualTo(clinics.size());
        assertThat(result.getContent().get(0).name()).isEqualTo(clinic1.getName());
        assertThat(result.getContent().get(1).name()).isEqualTo(clinic2.getName());
        verify(clinicRepository).findAll(pageRequest);
    }

    @Test
    void testGetAllClinics_withEmptyClinics_shouldReturnEmptyPageOfClinicRecord() {
        // Given
        List<Clinic> clinics = Collections.emptyList();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Clinic> clinicsPage = new PageImpl<>(clinics, pageRequest, 0);
        when(clinicRepository.findAll(pageRequest)).thenReturn(clinicsPage);
        // When
        Page<ClinicRecord> result = clinicService.getAllClinics(pageRequest);
        assertNotNull(result);
        assertThat(result.getContent().size()).isEqualTo(0);
        verify(clinicRepository).findAll(pageRequest);
    }

    @Test
    void testGetClinic_withExistingClinic_shouldReturnClinicRecord() {
        // Given
        Clinic clinic = new Clinic();
        Long clinicId = 1L;
        clinic.setId(clinicId)
                .setName("clinic_1")
                .setAddress("address_1")
                .setCity("city_1")
                .setPhoneNumber("0123456789")
                .setCreationDate(now());
        ClinicRecord clinicRecord = new ClinicRecord(1L, "clinic_1", "address_1", "city_1", "0123456789", now(), null);
        when(clinicRepository.findById(clinicId)).thenReturn(Optional.of(clinic));
        // When
        ClinicRecord result = clinicService.getClinic(clinicId);
        // Then
        assertNotNull(result);
        assertEquals(clinicRecord, result);
    }

    @Test
    void testGetClinic_withNoExistingClinic_shouldThrowClinicNotFoundException() {
        // Given
        Long clinicId = 1L;
        when(clinicRepository.findById(clinicId)).thenReturn(Optional.empty());
        // When
        ClinicNotFoundException exception = assertThrows(ClinicNotFoundException.class, () -> clinicService.getClinic(clinicId));
        // Then
        assertEquals("Clinic with id " + clinicId + " not found", exception.getMessage());
        assertEquals(NOT_FOUND, exception.getErrorCode());
        }

}
