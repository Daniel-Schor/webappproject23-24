package dev.userplaner.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for security settings in the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig{

    /**
     * Configures the security filter chain.
     * Disables Cross-Site Request Forgery (CSRF) protection.
     * 
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Die Cross-Site-Request-Forgery-Schutz wird deaktiviert
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}