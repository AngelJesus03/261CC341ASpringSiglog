package com._CC341ASpringSigconBackend.auth.service;

import com._CC341ASpringSigconBackend.auth.dto.AuthDto.AuthResponse;
import com._CC341ASpringSigconBackend.auth.dto.AuthDto.LoginRequest;
import com._CC341ASpringSigconBackend.auth.dto.AuthDto.PerfilResponse;
import com._CC341ASpringSigconBackend.auth.dto.AuthDto.RegistroRequest;

/**
 * Contrato del servicio de autenticación.
 *
 * Principios SOLID:
 *  - ISP: declara solo los métodos necesarios para HU01.
 *  - DIP: el controller y demás componentes dependen de esta interfaz,
 *         no de AuthServiceImpl directamente.
 *
 * Patrones GoF:
 *  - Facade: oculta la complejidad de Spring Security, BCrypt y JWT
 *            detrás de métodos simples de dominio (login, registrar, perfil).
 */
public interface IAuthService {

    /**
     * Autentica al usuario y devuelve un token JWT con su rol.
     * Criterio de aceptación HU01: inicio de sesión con email corporativo y contraseña.
     */
    AuthResponse login(LoginRequest request);

    /**
     * Registra un nuevo usuario (solo ADMIN puede hacerlo).
     * Criterio de aceptación HU01: gestión de usuarios y roles.
     */
    AuthResponse registrar(RegistroRequest request);

    /**
     * Devuelve el perfil del usuario autenticado actualmente.
     * Criterio de aceptación HU01: perfil con nombre, email y rol.
     */
    PerfilResponse obtenerPerfil(String email);
}
