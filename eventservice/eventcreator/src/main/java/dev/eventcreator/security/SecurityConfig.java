package dev.eventcreator.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain.
     *
     * This method configures the HttpSecurity object by disabling Cross-Site
     * Request Forgery (CSRF) protection. After the configuration, it builds the
     * HttpSecurity object into a SecurityFilterChain object and returns it.
     *
     * @param http The HttpSecurity object to be configured.
     * @return The configured SecurityFilterChain object.
     * @throws Exception If an error occurs during the configuration.
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Die Cross-Site-Request-Forgery-Schutz wird deaktiviert
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}