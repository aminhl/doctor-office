package org.nexthope.doctoroffice.appointment;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentConstants {

    public static final String APPOINTMENTS_ENDPOINT = "/appointments";

    public static final String APPOINTMENT_CREATED_MESSAGE = "Appointment created successfully";

    public static final String APPOINTMENT_UPDATED_MESSAGE = "Appointment updated successfully";

    public static final String APPOINTMENT_DELETED_MESSAGE = "Appointment deleted successfully";

    public static final String APPOINTMENTS_RETRIEVED_MESSAGE = "Appointments retrieved successfully";

    public static final String APPOINTMENT_RETRIEVED_MESSAGE = "Appointment retrieved successfully";

}
