package com._CC341ASpringSigconBackend.auth.service;

import com._CC341ASpringSigconBackend.auth.dto.AuthDto.AuthResponse;
import com._CC341ASpringSigconBackend.auth.dto.AuthDto.LoginRequest;
import com._CC341ASpringSigconBackend.auth.dto.AuthDto.PerfilResponse;
import com._CC341ASpringSigconBackend.auth.dto.AuthDto.RegistroRequest;
import com._CC341ASpringSigconBackend.auth.model.Usuario;
import com._CC341ASpringSigconBackend.auth.repository.UsuarioRepository;
import com._CC341ASpringSigconBackend.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Implementación del servicio de autenticación (HU01).
 *
 * Patrones GoF aplicados:
 *
 *  1. FACADE (principal):
 *     Oculta la complejidad de BCrypt, Spring Security AuthManager y JWT
 *     tras métodos simples: login(), registrar(), obtenerPerfil().
 *
 *  2. TEMPLATE METHOD (sutil):
 *     El método login() define el algoritmo general de autenticación;
 *     los pasos concretos (generar token, actualizar fecha) son ganchos
 *     que podrían sobreescribirse en subclases sin cambiar el flujo.
 *
 * Principios SOLID:
 *  - SRP: gestiona solo la lógica de autenticación y registro.
 *  - OCP: para añadir 2FA se extendería sin modificar esta clase.
 *  - LSP: puede reemplazar a IAuthService sin romper contratos.
 *  - DIP: depende de UsuarioRepository, JwtService, PasswordEncoder
 *         (abstracciones), no de implementaciones concretas.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // -------------------------------------------------------
    // HU01 Criterio 1: Inicio de sesión con email y contraseña
    // -------------------------------------------------------
    @Override
    public AuthResponse login(LoginRequest request) {
        // Spring Security valida credenciales (lanza excepción si son incorrectas)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // Cargamos el usuario y actualizamos su último acceso
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + request.email()));

        usuario.setUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario);

        // Generamos el token JWT con el rol embebido
        String token = jwtService.generarToken(usuario);

        return new AuthResponse(
                token,
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getRol().name(),
                "Bienvenido, " + usuario.getNombre()
        );
    }

    // -------------------------------------------------------
    // HU01 Criterio: Registro de usuario (solo ADMIN)
    // -------------------------------------------------------
    @Override
    public AuthResponse registrar(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new IllegalStateException("Ya existe un usuario con el email: " + request.email());
        }

        // Builder Pattern (Lombok) – construcción limpia de la entidad
        Usuario nuevoUsuario = Usuario.builder()
                .nombre(request.nombre())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .rol(request.rol())
                .activo(true)
                .build();

        usuarioRepository.save(nuevoUsuario);
        String token = jwtService.generarToken(nuevoUsuario);

        return new AuthResponse(
                token,
                nuevoUsuario.getEmail(),
                nuevoUsuario.getNombre(),
                nuevoUsuario.getRol().name(),
                "Usuario registrado exitosamente"
        );
    }

    // -------------------------------------------------------
    // HU01 Criterio: Perfil del usuario autenticado
    // -------------------------------------------------------
    @Override
    public PerfilResponse obtenerPerfil(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        return new PerfilResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol().name(),
                usuario.isActivo()
        );
    }
}
