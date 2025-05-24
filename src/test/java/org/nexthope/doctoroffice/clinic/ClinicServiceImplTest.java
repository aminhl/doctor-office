package org.nexthope.doctoroffice.clinic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {

    @Mock
    private ClinicRepository clinicRepository;

    @Mock
    private ClinicMapper clinicMapper;

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


}
