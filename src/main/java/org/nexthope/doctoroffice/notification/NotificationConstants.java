package org.nexthope.doctoroffice.notification;

import lombok.NoArgsConstructor;
import org.nexthope.doctoroffice.commons.GlobalConstants;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class NotificationConstants {

    public static final String NOTIFICATION_ENDPOINT = GlobalConstants.API_V1_PATH + "/notifications";

}
