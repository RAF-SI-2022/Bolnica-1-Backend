package raf.bolnica1.laboratory.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import raf.bolnica1.laboratory.exceptions.jwt.CantParseJwtException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${oauth.jwt.secret}")
    private String SECRET_KEY;

    public static JwtUtils getInstance(){
        return new JwtUtils();
    }
    public String getUsernameFromToken(String jwtToken) throws CantParseJwtException {
        return getClaimFromToken(jwtToken, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws CantParseJwtException {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) throws CantParseJwtException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new CantParseJwtException("Cant parse token, double check!");
        }
    }

    public boolean validateToken(String jwtToken) throws CantParseJwtException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(jwtToken);
            return !isTokenExpired(jwtToken);
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException e) {
            throw new CantParseJwtException("Invalid token");
        }
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(String jwtToken) throws CantParseJwtException {
        List<String> permissions = getClaimFromToken(jwtToken, claims -> claims.get("permissions", List.class));
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public java.util.Date getExpirationDateFromToken(String token) throws CantParseJwtException {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) throws CantParseJwtException {
        Instant instant = Instant.now();
        final java.util.Date expiration = getExpirationDateFromToken(token);
        return expiration.before(Date.from(instant));
    }

    private Key getKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
