package org.nexthope.doctoroffice.commons;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig {

    @Bean
    public CustomCommonsRequestLoggingFilter requestLoggingFilter(RequestLoggingProperties requestLoggingProperties) {
        CustomCommonsRequestLoggingFilter loggingFilter = new CustomCommonsRequestLoggingFilter();
        loggingFilter.setIncludeQueryString(requestLoggingProperties.includeQueryString());
        loggingFilter.setIncludePayload(requestLoggingProperties.includePayload());
        loggingFilter.setMaxPayloadLength(requestLoggingProperties.maxPayloadLength());
        loggingFilter.setIncludeHeaders(requestLoggingProperties.includeHeaders());
        loggingFilter.setIncludeClientInfo(requestLoggingProperties.includeClientInfo());
        return loggingFilter;
    }

}
