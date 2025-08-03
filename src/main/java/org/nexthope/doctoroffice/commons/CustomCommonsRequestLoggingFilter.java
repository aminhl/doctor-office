package org.nexthope.doctoroffice.commons;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import java.util.regex.Pattern;

public class CustomCommonsRequestLoggingFilter extends AbstractRequestLoggingFilter {

    private static final String ACTUATOR_URI = "/actuator/health";
    private static final Pattern ACTUATOR_LOGGING_PATTERN = Pattern.compile(ACTUATOR_URI);

    @Override
    protected void beforeRequest(@NonNull HttpServletRequest request, @NonNull String message) {
        logger.info(message);
    }

    @Override
    protected void afterRequest(@NonNull HttpServletRequest request, @NonNull String message) {
        logger.info(message);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        final String loggingPattern = request.getRequestURI();
        return ACTUATOR_LOGGING_PATTERN.matcher(loggingPattern).find();
    }
}
