package dev.hiruna.edutrack.util;

import dev.hiruna.edutrack.entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTAuthenticator {

    private final Dotenv dotenv;
    private final String jwtSecret;
    private final long jwtExpirationMs = 86400000; // 1 day in milliseconds

    public JWTAuthenticator() {
        dotenv = Dotenv.configure()
                .directory("./") // Ensure the correct directory is referenced
                .filename(".env") // Use proper filename for environment variables
                .load();

        jwtSecret = dotenv.get("SECRET_KEY");

        if (jwtSecret == null || jwtSecret.isEmpty()) {
            throw new IllegalStateException("JWT Secret Key is not configured in the .env file.");
        }
    }

    public String generateJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        String jwtToken = authToken.substring("Bearer ".length());
        try {
            Jwts.parser().setSigningKey(key()).build().parse(jwtToken);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        }
        return false;
    }
}
