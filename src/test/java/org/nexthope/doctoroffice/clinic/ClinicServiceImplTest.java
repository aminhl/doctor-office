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

    /*@Mock
    private ClinicRepository clinicRepository;

    @InjectMocks
    private ClinicServiceImpl clinicService;

    @Test
    void testCreateClinic_WithValidClinic_ShouldReturnClinicDto() {
        // Given
        Clinic clinic = new Clinic();
        clinic.setId(1L)
                .setName("clinic_1")
                .setAddress("address_1")
                .setCity("city_1")
                .setPhoneNumber("0123456789")
                .setCreationDate(now());
        ClinicDTO clinicDTO = new ClinicDTO(1L, "clinic_1", "address_1", "city_1", "0123456789", now(), null);
        when(clinicRepository.existsByNameAndAddressAndCity(clinic.getName(), clinic.getAddress(), clinic.getCity())).thenReturn(false);
        when(clinicRepository.save(clinic)).thenReturn(clinic);
        when(clinicMapper.toDto(clinic)).thenReturn(clinicDTO);
        // When
        ClinicDTO result = clinicService.createClinic(clinic);
        // Then
        assertNotNull(result);
        verify(clinicRepository).existsByNameAndAddressAndCity(clinic.getName(), clinic.getAddress(), clinic.getCity());
        verify(clinicMapper).toDto(clinic);
        verify(clinicRepository).save(clinic);
        assertEquals(clinicDTO, result);
    }

    @Test
    void testCreateClinic_WithExistingClinic_ShouldThrowClinicAlreadyExistsException() {
        // Given
        Clinic clinic = new Clinic();
        clinic.setId(1L)
                .setName("clinic_1")
                .setAddress("address_1")
                .setCity("city_1")
                .setPhoneNumber("0123456789")
                .setCreationDate(now());
        when(clinicRepository.existsByNameAndAddressAndCity(clinic.getName(), clinic.getAddress(), clinic.getCity())).thenReturn(true);
        // When
        ClinicAlreadyExistsException exception = assertThrows(ClinicAlreadyExistsException.class, () -> clinicService.createClinic(clinic));
        // Then
        assertEquals("Clinic " + clinic.getName() + " already exists", exception.getMessage());
        assertEquals(CONFLICT, exception.getErrorCode());
        verify(clinicRepository).existsByNameAndAddressAndCity(clinic.getName(), clinic.getAddress(), clinic.getCity());
        verify(clinicRepository, never()).save(clinic);
        verify(clinicMapper, never()).toDto(clinic);
    }

    @Test
    void testDeleteClinic_WithNoExistingClinic_ShouldThrowClinicNotFoundException() {
        // Given
        Long clinicId = 1L;
        when(clinicRepository.existsById(clinicId)).thenReturn(false);
        // When
        ClinicNotFoundException exception = assertThrows(ClinicNotFoundException.class, () -> clinicService.deleteClinic(clinicId));
        // Then
        assertEquals("Clinic with id " + clinicId + " not found", exception.getMessage());
        assertEquals(NOT_FOUND, exception.getErrorCode());
        verify(clinicRepository).existsById(clinicId);
        verify(clinicRepository, never()).deleteById(clinicId);
    }

    @Test
    void testDeleteClinic_WithExistingClinic_ShouldDeleteClinic() {
        // Given
        Long clinicId = 1L;
        when(clinicRepository.existsById(clinicId)).thenReturn(true);
        // When
        clinicService.deleteClinic(clinicId);
        // Then
        verify(clinicRepository).existsById(clinicId);
        verify(clinicRepository).deleteById(clinicId);
    }

    @Test
    void testGetAllClinics_WithNoEmptyClinics_ShouldReturnNoEmptyPageOfClinicDto() {
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
        when(clinicMapper.toDto(any(Clinic.class))).thenAnswer(invocation -> {
            Clinic clinic = invocation.getArgument(0);
            return new ClinicDTO(clinic.getId(), clinic.getName(), clinic.getAddress(), clinic.getCity(), clinic.getPhoneNumber(), clinic.getCreationDate(), clinic.getModificationDate());
        });
        // When
        Page<ClinicDTO> result = clinicService.getAllClinics(pageRequest);
        // Then
        assertNotNull(result);
        assertThat(result.getContent().size()).isEqualTo(clinics.size());
        assertThat(result.getContent().get(0).name()).isEqualTo(clinic1.getName());
        assertThat(result.getContent().get(1).name()).isEqualTo(clinic2.getName());
        verify(clinicRepository).findAll(pageRequest);
        verify(clinicMapper, times(clinics.size())).toDto(any(Clinic.class));
    }

    @Test
    void testGetAllClinics_WithEmptyClinics_ShouldReturnEmptyPageOfClinicDto() {
        // Given
        List<Clinic> clinics = Collections.emptyList();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Clinic> clinicsPage = new PageImpl<>(clinics, pageRequest, 0);
        when(clinicRepository.findAll(pageRequest)).thenReturn(clinicsPage);
        // When
        Page<ClinicDTO> result = clinicService.getAllClinics(pageRequest);
        assertNotNull(result);
        assertThat(result.getContent().size()).isEqualTo(0);
        verify(clinicRepository).findAll(pageRequest);
        verify(clinicMapper, never()).toDto(any(Clinic.class));
    }

    @Test
    void testGetClinic_WithExistingClinic_ShouldReturnClinicDto() {
        // Given
        Clinic clinic = new Clinic();
        Long clinicId = 1L;
        clinic.setId(clinicId)
                .setName("clinic_1")
                .setAddress("address_1")
                .setCity("city_1")
                .setPhoneNumber("0123456789")
                .setCreationDate(now());
        ClinicDTO clinicDTO = new ClinicDTO(1L, "clinic_1", "address_1", "city_1", "0123456789", now(), null);
        when(clinicRepository.findById(clinicId)).thenReturn(Optional.of(clinic));
        when(clinicMapper.toDto(clinic)).thenReturn(clinicDTO);
        // When
        ClinicDTO result = clinicService.getClinic(clinicId);
        // Then
        assertNotNull(result);
        verify(clinicRepository).findById(clinicId);
        verify(clinicMapper).toDto(clinic);
        assertEquals(clinicDTO, result);
    }

    @Test
    void testGetClinic_WithNoExistingClinic_ShouldThrowClinicNotFoundException() {
        // Given
        Long clinicId = 1L;
        when(clinicRepository.findById(clinicId)).thenReturn(Optional.empty());
        // When
        ClinicNotFoundException exception = assertThrows(ClinicNotFoundException.class, () -> clinicService.getClinic(clinicId));
        // Then
        assertEquals("Clinic with id " + clinicId + " not found", exception.getMessage());
        assertEquals(NOT_FOUND, exception.getErrorCode());
        verify(clinicRepository).findById(clinicId);
        verify(clinicMapper, never()).toDto(any(Clinic.class));
    }*/

}
