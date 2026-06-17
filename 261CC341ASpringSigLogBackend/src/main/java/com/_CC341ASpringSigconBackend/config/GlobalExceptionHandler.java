package com._CC341ASpringSigconBackend.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para LogiControl.
 *
 * Patrones GoF:
 *  - Template Method: cada método @ExceptionHandler define un paso concreto
 *    dentro del algoritmo general de construcción de respuestas de error.
 *
 * SOLID:
 *  - SRP: centraliza el manejo de errores; los controladores quedan limpios.
 *  - OCP: añadir un nuevo tipo de error solo requiere un nuevo método @ExceptionHandler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Error de credenciales inválidas en el login. */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
        return construirError(HttpStatus.UNAUTHORIZED, "Credenciales inválidas. Verifique su email y contraseña.");
    }

    /** Usuario no encontrado en la base de datos. */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UsernameNotFoundException ex) {
        return construirError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /** Acceso denegado por rol insuficiente. */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        return construirError(HttpStatus.FORBIDDEN, "No tiene permisos para acceder a este recurso.");
    }

    /** Violación de regla de negocio (ej: email duplicado). */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex) {
        return construirError(HttpStatus.CONFLICT, ex.getMessage());
    }

    /** Errores de validación (@Valid en los request bodies). */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> erroresCampos = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            erroresCampos.put(campo, mensaje);
        });

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", "Error de validación");
        respuesta.put("campos", erroresCampos);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    /** Cualquier otro error no manejado. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return construirError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor: " + ex.getMessage());
    }

    // -------------------------------------------------------
    // Helper: construye el mapa de error estándar
    // -------------------------------------------------------
    private ResponseEntity<Map<String, Object>> construirError(HttpStatus status, String mensaje) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("status", status.value());
        respuesta.put("error", status.getReasonPhrase());
        respuesta.put("mensaje", mensaje);
        return ResponseEntity.status(status).body(respuesta);
    }
}
