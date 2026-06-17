package com._CC341ASpringSigconBackend.auth.dto;

import com._CC341ASpringSigconBackend.auth.model.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTOs para la HU01 – Autenticación y Registro.
 *
 * SOLID – SRP: cada record tiene una única responsabilidad de transporte de datos.
 * Principio de segregación: se separan las operaciones de login, registro y respuesta.
 */
public class AuthDto {

    // -------------------------------------------------------
    // Request: iniciar sesión
    // -------------------------------------------------------
    public record LoginRequest(
            @Email(message = "El email debe ser válido")
            @NotBlank(message = "El email es obligatorio")
            String email,

            @NotBlank(message = "La contraseña es obligatoria")
            String password
    ) {}

    // -------------------------------------------------------
    // Request: registrar nuevo usuario (solo ADMIN)
    // -------------------------------------------------------
    public record RegistroRequest(
            @NotBlank(message = "El nombre es obligatorio")
            String nombre,

            @Email(message = "El email debe ser válido")
            @NotBlank(message = "El email es obligatorio")
            String email,

            @NotBlank(message = "La contraseña es obligatoria")
            @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
            String password,

            @NotNull(message = "El rol es obligatorio")
            Rol rol
    ) {}

    // -------------------------------------------------------
    // Response: devuelve el token JWT y datos básicos
    // -------------------------------------------------------
    public record AuthResponse(
            String token,
            String email,
            String nombre,
            String rol,
            String mensaje
    ) {}

    // -------------------------------------------------------
    // Response: perfil del usuario autenticado
    // -------------------------------------------------------
    public record PerfilResponse(
            Long id,
            String nombre,
            String email,
            String rol,
            boolean activo
    ) {}
}
