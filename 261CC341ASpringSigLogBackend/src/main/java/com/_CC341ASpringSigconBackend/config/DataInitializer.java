package com._CC341ASpringSigconBackend.config;

import com._CC341ASpringSigconBackend.auth.model.Rol;
import com._CC341ASpringSigconBackend.auth.model.Usuario;
import com._CC341ASpringSigconBackend.auth.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Inicializador de datos al arrancar la aplicación.
 *
 * Crea el usuario ADMIN inicial si no existe en la base de datos.
 * A partir de ese admin se pueden crear los demás usuarios via /api/auth/registro.
 *
 * AVISO: En producción cambia la contraseña por defecto inmediatamente.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!usuarioRepository.existsByEmail("admin@xyz.com")) {
            Usuario admin = Usuario.builder()
                    .nombre("Administrador LogiControl")
                    .email("admin@xyz.com")
                    .password(passwordEncoder.encode("Admin1234!"))
                    .rol(Rol.ADMIN)
                    .activo(true)
                    .build();

            usuarioRepository.save(admin);
            log.info("============================================");
            log.info("  USUARIO ADMIN CREADO");
            log.info("  Email   : admin@xyz.com");
            log.info("  Password: Admin1234!");
            log.info("  ⚠ Cambia la contraseña en producción");
            log.info("============================================");
        }
    }
}
