package com.learnhub.authservice.security;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.learnhub.authservice.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  public String generateToken(User user) {
    return Jwts.builder()
            .subject(user.getEmail())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 86400000))
            .signWith(getSignInKey())
            .compact();
}

    private String secretKey = "OnjEy+Wj5YQEc7JPO//XgvOg9KyvmR+ADxUObgn8OhOgOjKGLGQwPUXU7WKkLSscDzF5rYf/nWzALoby9qRBMA=="; 

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

        public boolean isTokenValid(String token, UserDetails userDetails) {

        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }
    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }
    
}
