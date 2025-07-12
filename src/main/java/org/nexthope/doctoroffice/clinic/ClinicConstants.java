package org.nexthope.doctoroffice.clinic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.nexthope.doctoroffice.commons.GlobalConstants;

import static org.nexthope.doctoroffice.commons.GlobalConstants.API_V1_PATH;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClinicConstants {

    public static final String CLINICS_ENDPOINT = API_V1_PATH + "/clinics";

}
