package com._CC341ASpringSigconBackend.auth.pattern;

import com._CC341ASpringSigconBackend.auth.model.Rol;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

/**
 * Patrón SINGLETON (GoF) – Registro central de permisos por rol (RBAC).
 *
 * Propósito en HU01:
 *  Garantizar que exista una única instancia que defina qué rutas
 *  puede acceder cada rol del sistema LogiControl.
 *
 * Se usa de forma thread-safe mediante el idioma "Initialization-on-demand holder".
 *
 * SOLID:
 *  - SRP: única responsabilidad → mapear roles a rutas permitidas.
 *  - OCP: añadir permisos solo requiere modificar el mapa de inicialización,
 *         no la lógica de consulta.
 */
public final class RolPermisoRegistry {

    // Mapa inmutable: Rol → conjunto de prefijos de ruta permitidos
    private final Map<Rol, Set<String>> permisosPorRol;

    private RolPermisoRegistry() {
        Map<Rol, Set<String>> mapa = new EnumMap<>(Rol.class);

        mapa.put(Rol.JEFE_LOGISTICA, Set.of(
                "/api/compras/**",
                "/api/cotizaciones/**",
                "/api/ordenes-compra/**",
                "/api/proveedores/**"
        ));

        mapa.put(Rol.ASISTENTE_LOGISTICA, Set.of(
                "/api/recepciones/**",
                "/api/internamiento/**",
                "/api/devoluciones/**"
        ));

        mapa.put(Rol.TESORERO, Set.of(
                "/api/obligaciones/**",
                "/api/pagos/**"
        ));

        mapa.put(Rol.GERENTE_FINANCIERO, Set.of(
                "/api/pagos/aprobar/**",
                "/api/cheques/**",
                "/api/dashboard/**"
        ));

        mapa.put(Rol.GERENTE_GENERAL, Set.of(
                "/api/cheques/firmar/**",
                "/api/dashboard/**",
                "/api/reportes/**"
        ));

        mapa.put(Rol.CONTADOR, Set.of(
                "/api/contabilidad/**",
                "/api/asientos/**"
        ));

        mapa.put(Rol.ADMIN, Set.of(
                "/api/**"          // acceso total
        ));

        this.permisosPorRol = Collections.unmodifiableMap(mapa);
    }

    // ---  Initialization-on-demand holder (thread-safe Singleton) ---
    private static final class Holder {
        private static final RolPermisoRegistry INSTANCIA = new RolPermisoRegistry();
    }

    public static RolPermisoRegistry getInstance() {
        return Holder.INSTANCIA;
    }

    // -------------------------------------------------------
    // API pública
    // -------------------------------------------------------

    /** Devuelve las rutas que puede acceder un rol dado. */
    public Set<String> rutasPermitidas(Rol rol) {
        return permisosPorRol.getOrDefault(rol, Collections.emptySet());
    }

    /** Verifica si un rol tiene acceso a una ruta dada (comparación por prefijo). */
    public boolean tienePermiso(Rol rol, String ruta) {
        return rutasPermitidas(rol).stream()
                .anyMatch(patron -> ruta.startsWith(patron.replace("/**", "")));
    }
}
