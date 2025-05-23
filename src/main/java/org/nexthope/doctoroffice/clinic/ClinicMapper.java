package org.nexthope.doctoroffice.clinic;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClinicMapper {

    ClinicDTO toDto(Clinic clinic);

}
