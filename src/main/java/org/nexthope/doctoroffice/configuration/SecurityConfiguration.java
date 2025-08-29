package org.nexthope.doctoroffice.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {

    private final KeycloakJwtAuthenticationConverter keycloakJwtAuthenticationConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("SecurityFilterChain - START");
        http.csrf(AbstractHttpConfigurer::disable)
            .cors(withDefaults())
            .authorizeHttpRequests(
                    req -> req.requestMatchers(
                    "/swagger-ui/**"
            )
                .permitAll()
                .anyRequest()
                .authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(token ->
                    token.jwtAuthenticationConverter(keycloakJwtAuthenticationConverter)));
        log.debug("SecurityFilterChain - END");
        return http.build();
    }

}
