package com.myportfolio.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.myportfolio.backend.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(
                        HttpSecurity http) throws Exception {

                http
                                // Disable CSRF because this is a REST API
                                .csrf(csrf -> csrf.disable())

                                // Stateless JWT authentication
                                .sessionManagement(session -> session.sessionCreationPolicy(
                                                SessionCreationPolicy.STATELESS))

                                .authorizeHttpRequests(auth -> auth

                                                // Login is public
                                                .requestMatchers(
                                                                "/api/auth/**")
                                                .permitAll()

                                                //*  */ CONTACT
                                            
                                                // POST contact - PUBLIC
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/contact")
                                                .permitAll()

                                                // GET contact + GET contact/{id} - ADMIN
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/contact/**")
                                                .hasRole("ADMIN")

                                                // DELETE contact/{id} - ADMIN
                                                .requestMatchers(
                                                                HttpMethod.DELETE,
                                                                "/api/contact/**")
                                                .hasRole("ADMIN")
                                                //* */ end CONTACT

                                                // Public GET APIs
                                                .requestMatchers(
                                                                HttpMethod.GET,
                                                                "/api/**")
                                                .permitAll()

                                                // Admin only operations
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(
                                                                HttpMethod.PUT,
                                                                "/api/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(
                                                                HttpMethod.PATCH,
                                                                "/api/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(
                                                                HttpMethod.DELETE,
                                                                "/api/**")
                                                .hasRole("ADMIN")

                                                // Everything else
                                                .anyRequest().authenticated())

                                .addFilterBefore(
                                                jwtAuthenticationFilter,
                                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {

                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration configuration)
                        throws Exception {

                return configuration.getAuthenticationManager();
        }

}
