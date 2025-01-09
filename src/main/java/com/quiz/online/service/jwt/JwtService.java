package com.quiz.online.service.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key key;

    @jakarta.annotation.PostConstruct
    public void init() {
        // Initialize key after signerKey is injected
        this.key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(signerKey.getBytes()));
    }

    public String generateToken(String username, String role , String userId , String avatarUrl) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
            .setSubject(username)
            .claim("role", role)
            .claim("userId", userId)
            .claim("avatarUrl", avatarUrl)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.out.println("Token has expired: " + e.getMessage());
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            System.out.println("Invalid token: " + e.getMessage());
        } catch (io.jsonwebtoken.SignatureException e) {
            System.out.println("Invalid signature: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Token validation error: " + e.getMessage());
        }
        return false;
    }
}
