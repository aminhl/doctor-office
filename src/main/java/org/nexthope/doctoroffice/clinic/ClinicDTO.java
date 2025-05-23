package org.nexthope.doctoroffice.clinic;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClinicDTO(Long id,
                        String name,
                        String address,
                        String city,
                        String phoneNumber,
                        LocalDateTime creationDate,
                        LocalDateTime lastModifiedDate) {
}
