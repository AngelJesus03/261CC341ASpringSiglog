package com._CC341ASpringSigconBackend.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio de tokens JWT para LogiControl.
 *
 * Patrones GoF aplicados:
 *  - Strategy: encapsula el algoritmo de firma/validación del token.
 *    Puede intercambiarse sin afectar al resto del sistema.
 *
 * Principios SOLID:
 *  - SRP: única responsabilidad → operar con tokens JWT.
 *  - OCP: abierto a extensión (nuevos claims, algoritmos) sin modificar clientes.
 */
@Service
public class JwtService {

    @Value("${logicontrol.jwt.secret}")
    private String secretKey;

    @Value("${logicontrol.jwt.expiration-ms}")
    private long expirationMs;

    // -------------------------------------------------------
    // Generación de tokens
    // -------------------------------------------------------

    /** Genera un token JWT para el usuario dado, añadiendo su rol como claim. */
    public String generarToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        // Añadimos el rol como claim personalizado para RBAC en el frontend
        extraClaims.put("rol", userDetails.getAuthorities().iterator().next().getAuthority());
        return generarToken(extraClaims, userDetails);
    }

    public String generarToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    // -------------------------------------------------------
    // Validación de tokens
    // -------------------------------------------------------

    /** Valida que el token pertenezca al usuario y no haya expirado. */
    public boolean esTokenValido(String token, UserDetails userDetails) {
        final String username = extraerUsername(token);
        return username.equals(userDetails.getUsername()) && !estaExpirado(token);
    }

    // -------------------------------------------------------
    // Extracción de claims
    // -------------------------------------------------------

    public String extraerUsername(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    public <T> T extraerClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extraerTodosLosClaims(token);
        return claimsResolver.apply(claims);
    }

    // -------------------------------------------------------
    // Helpers privados
    // -------------------------------------------------------

    private boolean estaExpirado(String token) {
        return extraerClaim(token, Claims::getExpiration).before(new Date());
    }

    private Claims extraerTodosLosClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(
                java.util.Base64.getEncoder().encodeToString(secretKey.getBytes())
        );
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
