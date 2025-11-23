package com.talkgenius.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT Token Provider.
 *
 * <p>Handles JWT token generation, validation, and claims extraction.
 * Uses JJWT library with HS256 algorithm.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.access-expiration:3600}")
    private long accessTokenExpiration; // seconds

    @Value("${security.jwt.refresh-expiration:2592000}")
    private long refreshTokenExpiration; // seconds

    private SecretKey secretKey;

    /**
     * Initialize the secret key after properties are loaded.
     */
    @PostConstruct
    public void init() {
        // Ensure the secret is at least 256 bits (32 bytes) for HS256
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate access token for authenticated user.
     *
     * @param authentication Spring Security authentication object
     * @return JWT access token
     */
    public String generateAccessToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return generateToken(userPrincipal.getId(), accessTokenExpiration);
    }

    /**
     * Generate access token from user ID.
     *
     * @param userId The user ID
     * @return JWT access token
     */
    public String generateAccessToken(String userId) {
        return generateToken(userId, accessTokenExpiration);
    }

    /**
     * Generate refresh token from user ID.
     *
     * @param userId The user ID
     * @return JWT refresh token
     */
    public String generateRefreshToken(String userId) {
        return generateToken(userId, refreshTokenExpiration);
    }

    /**
     * Generate JWT token with specified expiration.
     *
     * @param userId The user ID
     * @param expirationSeconds Expiration time in seconds
     * @return JWT token
     */
    private String generateToken(String userId, long expirationSeconds) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationSeconds * 1000);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    /**
     * Extract user ID from JWT token.
     *
     * @param token JWT token
     * @return User ID
     */
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    /**
     * Validate JWT token.
     *
     * @param authToken JWT token
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (SecurityException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        }
        return false;
    }
}
