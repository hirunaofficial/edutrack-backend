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

    Dotenv dotenv = Dotenv.configure()
            .directory("/src/main/resources")
            .filename(".env") //
            .load();

    //node -e "console.log(require('crypto').randomBytes(32).toString('hex'))"
    private final String jwtSecret = dotenv.get("SECRET_KEY"); // Secret Key
    private final long jwtExpirationMs = 86400000; // 1 day in milliseconds

    public String generateJwtToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
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