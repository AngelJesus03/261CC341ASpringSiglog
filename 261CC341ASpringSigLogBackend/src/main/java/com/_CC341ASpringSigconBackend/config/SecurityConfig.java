package com._CC341ASpringSigconBackend.config;

import com._CC341ASpringSigconBackend.auth.repository.UsuarioRepository;
import com._CC341ASpringSigconBackend.auth.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuracion central de Spring Security para LogiControl.
 *
 * Patrones GoF:
 *  - Chain of Responsibility: cadena de filtros de Spring Security
 *    (JwtAuthFilter -> AuthenticationFilter -> ...).
 *  - Factory Method: cada @Bean actua como fabrica de componentes.
 *
 * SOLID:
 *  - SRP: solo configura la seguridad HTTP; la logica queda en servicios.
 *  - DIP: depende de abstracciones (JwtAuthFilter, UserDetailsService).
 *
 * Reglas RBAC (HU01):
 *  - /api/auth/**          publico (login)
 *  - /api/compras/**       JEFE_LOGISTICA, ADMIN
 *  - /api/recepciones/**   ASISTENTE_LOGISTICA, JEFE_LOGISTICA, ADMIN
 *  - /api/obligaciones/**  TESORERO, ADMIN
 *  - /api/pagos/**         TESORERO, GERENTE_FINANCIERO, ADMIN
 *  - /api/cheques/**       GERENTE_FINANCIERO, GERENTE_GENERAL, ADMIN
 *  - /api/contabilidad/**  CONTADOR, ADMIN
 *  - /api/admin/**         solo ADMIN
 *
 * Nota: en Spring Security 7 (incluido en Spring Boot 4) el
 * DaoAuthenticationProvider recibe el UserDetailsService por
 * constructor en lugar de por setter.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UsuarioRepository usuarioRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // Publico: login + Swagger
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                ).permitAll()

                // Compras - Jefe de Logistica
                .requestMatchers("/api/compras/**", "/api/cotizaciones/**",
                                 "/api/ordenes-compra/**", "/api/proveedores/**")
                    .hasAnyRole("JEFE_LOGISTICA", "ADMIN")

                // Recepcion - Asistente de Logistica
                .requestMatchers("/api/recepciones/**", "/api/internamiento/**",
                                 "/api/devoluciones/**")
                    .hasAnyRole("ASISTENTE_LOGISTICA", "JEFE_LOGISTICA", "ADMIN")

                // Tesoreria
                .requestMatchers("/api/obligaciones/**")
                    .hasAnyRole("TESORERO", "ADMIN")
                .requestMatchers("/api/pagos/**")
                    .hasAnyRole("TESORERO", "GERENTE_FINANCIERO", "ADMIN")

                // Cheques
                .requestMatchers("/api/cheques/**")
                    .hasAnyRole("GERENTE_FINANCIERO", "GERENTE_GENERAL", "ADMIN")

                // Contabilidad
                .requestMatchers("/api/contabilidad/**", "/api/asientos/**")
                    .hasAnyRole("CONTADOR", "ADMIN")

                // Reportes y dashboard
                .requestMatchers("/api/dashboard/**")
                    .hasAnyRole("GERENTE_FINANCIERO", "GERENTE_GENERAL", "ADMIN")
                .requestMatchers("/api/reportes/**")
                    .hasAnyRole("GERENTE_GENERAL", "ADMIN")

                // Administracion de usuarios
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            // JWT = sin estado; no se usan sesiones HTTP
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Spring Security 7: DaoAuthenticationProvider recibe el
     * UserDetailsService por constructor (la API antigua quedo deprecada).
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
