package org.nexthope.doctoroffice.appointment;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentDTO toDto(Appointment appointment);

}
