package dev.eventplaner.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class represents the security configuration for the application.
 * It is annotated with @Configuration to indicate that it is a configuration
 * class.
 * It is also annotated with @EnableWebSecurity to enable Spring Security's web
 * security support.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * This method configures the SecurityFilterChain.
     * It disables the Cross-Site Request Forgery (CSRF) protection.
     *
     * @param http The HttpSecurity object to modify.
     * @return The SecurityFilterChain object that has been built.
     * @throws Exception If an error occurs during the configuration.
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Die Cross-Site-Request-Forgery-Schutz wird deaktiviert
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}