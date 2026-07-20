package github.patrik1339.backend.security;

import github.patrik1339.backend.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtGenerator {
    private final String SECRET_STRING = "AgrB1389414ihdaDQW492DQH3Dhd13#@SecretKeyToMakeItLongEnough!";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

    public String generateJwt(UserDTO userDTO) {
        return Jwts.builder()
                .issuer("franchise-manager")
                .subject(userDTO.getEmail())
                .claim("role", userDTO.getUserRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }
}