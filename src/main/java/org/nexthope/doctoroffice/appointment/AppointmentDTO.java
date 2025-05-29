package org.nexthope.doctoroffice.appointment;

import org.nexthope.doctoroffice.user.User;

import java.time.LocalDateTime;

public record AppointmentDTO(Long id,
                             User doctor,
                             User patient,
                             LocalDateTime startTime,
                             LocalDateTime endTime,
                             String notes,
                             Status status
                             ) {
}
