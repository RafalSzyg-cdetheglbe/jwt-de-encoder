package com.JwtDecryptor.app;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTgenerator {
    public static String generateJWTToken(String email, String username) {
        // Generate a secret key for signing the JWT
        var secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .claim("email", email)
                .claim("username", username)
                .signWith(secretKey)
                .compact();
    }
}
