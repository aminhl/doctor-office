package org.nexthope.doctoroffice.clinic;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClinicRecord(Long id,
                           String name,
                           String address,
                           String city,
                           String phoneNumber,
                           LocalDateTime creationDate,
                           LocalDateTime lastModifiedDate) {

    public Clinic toClinic() {
        return new Clinic()
                .setId(id)
                .setName(name)
                .setAddress(address)
                .setCity(city)
                .setPhoneNumber(phoneNumber)
                .setCreationDate(creationDate)
                .setModificationDate(lastModifiedDate);
    }
}
