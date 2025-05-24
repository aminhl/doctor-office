package org.nexthope.doctoroffice.clinic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClinicConstants {

    public static final String CLINICS_ENDPOINT = "/clinics";

    public static final String CLINIC_CREATED_MESSAGE = "Clinic created successfully";

    public static final String CLINIC_UPDATED_MESSAGE = "Clinic updated successfully";

    public static final String CLINIC_DELETED_MESSAGE = "Clinic deleted successfully";

}
