package com._CC341ASpringSigconBackend.auth.pattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Patrón OBSERVER (GoF) – Bitácora de eventos de autenticación.
 *
 * Propósito en HU01:
 *  Registrar automáticamente los intentos de inicio de sesión (exitosos y fallidos)
 *  sin acoplar esa lógica al servicio de autenticación.
 *
 * Componentes:
 *  - LoginEventObserver (interfaz): contrato del observador.
 *  - LoginEventPublisher (subject): publica eventos a todos los observadores.
 *  - ConsolaLoginObserver: observador concreto que imprime en log.
 *
 * SOLID:
 *  - OCP: añadir un nuevo tipo de registro (base de datos, email) solo requiere
 *         una nueva clase que implemente LoginEventObserver, sin tocar el publicador.
 *  - DIP: AuthServiceImpl depende de LoginEventPublisher, no de observadores concretos.
 */
public class LoginEventPublisher {

    // -------------------------------------------------------
    // Interfaz Observer
    // -------------------------------------------------------
    public interface LoginEventObserver {
        void onLoginExitoso(String email, String rol, LocalDateTime momento);
        void onLoginFallido(String email, String motivo, LocalDateTime momento);
    }

    // -------------------------------------------------------
    // Subject (publicador)
    // -------------------------------------------------------
    @Component
    @Slf4j
    public static class AuthEventPublisher {

        private final List<LoginEventObserver> observadores = new ArrayList<>();

        public void registrarObservador(LoginEventObserver observador) {
            observadores.add(observador);
        }

        public void publicarLoginExitoso(String email, String rol) {
            LocalDateTime ahora = LocalDateTime.now();
            observadores.forEach(o -> o.onLoginExitoso(email, rol, ahora));
        }

        public void publicarLoginFallido(String email, String motivo) {
            LocalDateTime ahora = LocalDateTime.now();
            observadores.forEach(o -> o.onLoginFallido(email, motivo, ahora));
        }
    }

    // -------------------------------------------------------
    // Observador concreto: registra en la consola / log
    // -------------------------------------------------------
    @Component
    @Slf4j
    public static class ConsolaLoginObserver implements LoginEventObserver {

        public ConsolaLoginObserver(AuthEventPublisher publisher) {
            publisher.registrarObservador(this);
        }

        @Override
        public void onLoginExitoso(String email, String rol, LocalDateTime momento) {
            log.info("[AUTH] LOGIN EXITOSO | usuario={} | rol={} | momento={}", email, rol, momento);
        }

        @Override
        public void onLoginFallido(String email, String motivo, LocalDateTime momento) {
            log.warn("[AUTH] LOGIN FALLIDO | usuario={} | motivo={} | momento={}", email, motivo, momento);
        }
    }
}
