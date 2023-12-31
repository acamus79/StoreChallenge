package com.aec.store.config;

import com.aec.store.utils.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.aec.store.enums.Role.*;
import static com.aec.store.enums.Permission.*;
import static org.springframework.http.HttpMethod.*;

/**
 * Configuration class for security settings in the application.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private static final String[] PUBLIC = {"/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources","/swagger-resources/**","/configuration/ui","/configuration/security","/swagger-ui/**","/webjars/**","/swagger-ui.html"};

    /**
     * Defines the security filter chain configuration.
     *
     * @param http The HttpSecurity object to configure security settings.
     * @return The SecurityFilterChain object.
     * @throws Exception If there's an error configuring security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                // Public routes without authentication
                .requestMatchers(PUBLIC).permitAll()

                // Admin Routes
                .requestMatchers("/api/v1/data/**").hasAnyRole(ADMIN.name())
                .requestMatchers(GET, "/api/v1/data/**").hasAnyAuthority(ADMIN_READ.name())
                .requestMatchers(POST, "/api/v1/data/**").hasAnyAuthority(ADMIN_CREATE.name())
                .requestMatchers(PUT, "/api/v1/data/**").hasAnyAuthority(ADMIN_UPDATE.name())
                .requestMatchers(DELETE, "/api/v1/data/**").hasAnyAuthority(ADMIN_DELETE.name())
                .anyRequest()
                .authenticated()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
