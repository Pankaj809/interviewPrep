package com.example.interviewPrep.jwt;

import com.example.interviewPrep.model.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Key SECRET_KEY =
            stringToKey("yourlongerbaseencodedkeystringheredafasdfadsfsdfasdfadsfasdfasdfasdf");

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60 * 24);
        return Jwts.builder()
                .claim("email", user.getEmail())
                .claim("userKey", user.getId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SECRET_KEY)
                .compact();
    }



    public boolean getClaimsFromToken(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.isEmpty();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid Token");
        }
    }

    public static Key stringToKey(String keyString) {
        byte[] decodedKey = Base64.getDecoder().decode(keyString);
        Key key = Keys.hmacShaKeyFor(decodedKey);
        return key;
    }
}
