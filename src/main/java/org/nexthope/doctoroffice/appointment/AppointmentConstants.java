package org.nexthope.doctoroffice.appointment;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.nexthope.doctoroffice.commons.GlobalConstants;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentConstants {

    public static final String APPOINTMENTS_ENDPOINT = GlobalConstants.API_V1_PATH + "/appointments";

}
