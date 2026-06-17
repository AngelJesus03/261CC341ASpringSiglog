package com._CC341ASpringSigconBackend.auth.model;

/**
 * SOLID – Single Responsibility: enumera solo los roles del sistema.
 *
 * Roles de la HU01:
 *  - JEFE_LOGISTICA   → Gestión de compras, cotizaciones, OC
 *  - ASISTENTE_LOGISTICA → Recepción de mercadería, internamiento
 *  - TESORERO         → Registro de obligaciones, pagos
 *  - GERENTE_FINANCIERO → Aprobación de pagos, cheques
 *  - GERENTE_GENERAL  → Firma de cheques
 *  - CONTADOR         → Asientos contables
 *  - ADMIN            → Gestión de usuarios/roles
 */
public enum Rol {
    JEFE_LOGISTICA,
    ASISTENTE_LOGISTICA,
    TESORERO,
    GERENTE_FINANCIERO,
    GERENTE_GENERAL,
    CONTADOR,
    ADMIN
}
