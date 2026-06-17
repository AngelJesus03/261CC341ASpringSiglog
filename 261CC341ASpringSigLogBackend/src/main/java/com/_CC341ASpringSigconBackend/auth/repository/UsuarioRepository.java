package com._CC341ASpringSigconBackend.auth.repository;

import com._CC341ASpringSigconBackend.auth.model.Rol;
import com._CC341ASpringSigconBackend.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Usuario.
 *
 * SOLID – DIP: el servicio depende de esta abstracción (interfaz),
 * no de una implementación concreta.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /** Busca un usuario por email (usado por Spring Security). */
    Optional<Usuario> findByEmail(String email);

    /** Verifica si ya existe un usuario con ese email. */
    boolean existsByEmail(String email);

    /** Filtra usuarios por rol (útil para RBAC). */
    List<Usuario> findByRol(Rol rol);
}
