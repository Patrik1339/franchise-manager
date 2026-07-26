package github.patrik1339.backend.security;

import github.patrik1339.backend.dto.UserDTO;
import github.patrik1339.backend.enums.SystemRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey key;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwt(UserDTO userDTO) {
        return Jwts.builder()
                .issuer("franchise-manager")
                .subject(userDTO.getId().toString())
                .claim("email", userDTO.getEmail())
                .claim("systemRole", userDTO.getSystemRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000L))
                .signWith(key)
                .compact();

    }

    public Claims getClaimsFromJwt(String jwt) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public boolean isValid(String jwt) {
        try {
            Claims claims = getClaimsFromJwt(jwt);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Long getUserIdFromJwt(String jwt) {
        return Long.parseLong(getClaimsFromJwt(jwt).getSubject());
    }

    public String getUserEmailFromJwt(String jwt) {
        return getClaimsFromJwt(jwt).get("email", String.class);
    }

    public SystemRole getSystemRoleFromJwt(String jwt) {
        return SystemRole.valueOf(getClaimsFromJwt(jwt).get("systemRole", String.class));
    }
}