package org.nexthope.doctoroffice.commons;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "logging.request")
public record RequestLoggingProperties(
        boolean includeClientInfo,
        boolean includeQueryString,
        boolean includeHeaders,
        boolean includePayload,
        int maxPayloadLength
) {
    public RequestLoggingProperties {
        if (maxPayloadLength < 0) {
            throw new IllegalArgumentException("maxPayloadLength must be greater than or equal to 0");
        }
    }
}
