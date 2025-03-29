package com.api.userManagement.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * Provider for JWT token generation and validation.
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwt.secret:jwt.secret = Aq3t6w9z$C&F)J@NcRfUjXn2r5u8x/A?}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms:86400000}") // Default: 24 hours
    private int jwtExpirationMs;

    /**
     * Generate JWT token.
     *
     * @param authentication Authentication object
     * @return JWT token
     */
    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Get user email from JWT token.
     *
     * @param token JWT token
     * @return User email
     */
    public String getUserEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Validate JWT token.
     *
     * @param authToken JWT token
     * @return True if valid
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(authToken);
            logger.debug("JWT validé avec succès");
            return true;
        } catch (SignatureException ex) {
            logger.error("Signature JWT invalide. Secret utilisé: {}", jwtSecret);
        } catch (MalformedJwtException ex) {
            logger.error("Token JWT malformé: {}", authToken);
        } catch (ExpiredJwtException ex) {
            logger.error("Token JWT expiré. Date d'expiration: {}", ex.getClaims().getExpiration());
        } catch (UnsupportedJwtException ex) {
            logger.error("Token JWT non supporté: {}", authToken);
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string est vide");
        }
        return false;
    }

    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}