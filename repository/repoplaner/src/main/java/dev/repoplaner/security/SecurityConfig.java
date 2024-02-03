package dev.repoplaner.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for security settings in the web application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain for the application.
     * This bean is responsible for handling HTTP security configurations,
     * including disabling Cross-Site Request Forgery (CSRF) protection.
     *
     * @param http the HttpSecurity object used to configure the security filter
     *             chain
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while configuring the security filter
     *                   chain
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Die Cross-Site-Request-Forgery-Schutz wird deaktiviert
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}