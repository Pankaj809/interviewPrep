package com.example.interviewPrep.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "yoursecretkeyfdklfjaklsfjdaslkhewjhfewpaisjfhdsjkfhewpfahdsjkhew";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    // Extract claims from token
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract specific claim
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract username from token
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract email from token
    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }

    // Extract userId from token
    public String extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", String.class));
    }

    // Check if token has expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generate token
    public String generateToken(String userName, String email, String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", userName);
        claims.put("email", email);
        claims.put("userId", userId);
        return createToken(claims, userName);
    }

    // Create token
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
