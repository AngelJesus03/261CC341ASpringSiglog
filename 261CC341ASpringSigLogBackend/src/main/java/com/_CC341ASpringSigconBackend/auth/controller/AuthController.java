package com._CC341ASpringSigconBackend.auth.controller;

import com._CC341ASpringSigconBackend.auth.dto.AuthDto.AuthResponse;
import com._CC341ASpringSigconBackend.auth.dto.AuthDto.LoginRequest;
import com._CC341ASpringSigconBackend.auth.dto.AuthDto.PerfilResponse;
import com._CC341ASpringSigconBackend.auth.dto.AuthDto.RegistroRequest;
import com._CC341ASpringSigconBackend.auth.pattern.LoginEventPublisher.AuthEventPublisher;
import com._CC341ASpringSigconBackend.auth.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST – HU01: Registro e inicio de sesión.
 *
 * Endpoints:
 *  POST /api/auth/login         → iniciar sesión (público)
 *  POST /api/auth/registro      → registrar usuario (solo ADMIN)
 *  GET  /api/auth/perfil        → ver mi perfil (autenticado)
 *
 * Patrones GoF:
 *  - Facade: delega toda la lógica a IAuthService (Facade del subsistema de seguridad).
 *
 * SOLID:
 *  - SRP: solo maneja peticiones HTTP; la lógica está en el servicio.
 *  - DIP: depende de IAuthService (abstracción), no de AuthServiceImpl.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;
    private final AuthEventPublisher eventPublisher;

    /**
     * HU01 – Criterio 1: Inicio de sesión con email y contraseña.
     * Devuelve un token JWT con el rol embebido.
     *
     * POST /api/auth/login
     * Body: { "email": "jefe@xyz.com", "password": "miPassword123" }
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse respuesta = authService.login(request);
            eventPublisher.publicarLoginExitoso(request.email(), respuesta.rol());
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            eventPublisher.publicarLoginFallido(request.email(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * HU01 – Criterio: Registro de nuevo usuario (solo ADMIN).
     *
     * POST /api/auth/registro
     * Header: Authorization: Bearer <token_admin>
     * Body: { "nombre": "Ana López", "email": "ana@xyz.com",
     *         "password": "secret123", "rol": "TESORERO" }
     */
    @PostMapping("/registro")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> registrar(@Valid @RequestBody RegistroRequest request) {
        AuthResponse respuesta = authService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    /**
     * HU01 – Criterio: Ver perfil del usuario autenticado.
     *
     * GET /api/auth/perfil
     * Header: Authorization: Bearer <token>
     */
    @GetMapping("/perfil")
    public ResponseEntity<PerfilResponse> obtenerPerfil(Authentication authentication) {
        PerfilResponse perfil = authService.obtenerPerfil(authentication.getName());
        return ResponseEntity.ok(perfil);
    }
}
