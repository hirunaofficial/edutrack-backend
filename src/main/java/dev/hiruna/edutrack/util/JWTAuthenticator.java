package dev.hiruna.edutrack.util;

import dev.hiruna.edutrack.entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTAuthenticator {

    private final String jwtSecret;
    private final long jwtExpirationMs = 86400000; // 1 day in milliseconds

    public JWTAuthenticator() {
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .filename(".env")
                .load();

        jwtSecret = dotenv.get("SECRET_KEY");

        if (jwtSecret == null || jwtSecret.isEmpty()) {
            throw new IllegalStateException("JWT Secret Key is not configured in the .env file.");
        }
    }

    public String generateJwtToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();  // Generate the JWT string
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public boolean validateJwtToken(String authToken) {
        if (!authToken.startsWith("Bearer ")) {
            return false;
        }

        String jwtToken = authToken.substring(7);

        try {
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parseSignedClaims(jwtToken);

            return true;
        } catch (JwtException e) {
            System.out.println("Invalid JWT: " + e.getMessage());
        }
        return false;
    }
}