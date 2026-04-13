package com.api.image_processing_event_driven.service;

import com.api.image_processing_event_driven.model.entity.User;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;

@Setter
@Getter
@Service
@ConfigurationProperties(prefix = "spring.security.jwt")
public class JwtService {

    private long expiration;
    private String secretKey;
    private long refresh_token_expiration;

    private SecretKey getSignInKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey) ;
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    public String generateToken(final User user) {
        return buildToken(user, expiration, "access");
    }

    public String generateRefreshToken(final User user) {
        return buildToken(user, refresh_token_expiration, "refresh");
    }

    private String buildToken(final User user, final long expiration, String type) {
        return Jwts
            .builder()
            .subject(user.getUsername())
            .claim("type", type)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey())
            .compact();
    }

    public boolean isTokenValid(String token, User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token); //same username and not expired token
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration();
    }

    public String extractTokenType(String token) {
        return Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("type", String.class);
    }

}
