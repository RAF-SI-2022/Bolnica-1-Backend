package raf.bolnica1.employees.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import raf.bolnica1.employees.exceptionHandler.exceptions.jwt.CantParseJwtException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${oauth.jwt.secret}")
    private String SECRET_KEY;
    private static final Long TOKEN_EXPIRATION_MIN = 60L;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("permissions", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return generateToken(claims, userDetails.getUsername());
    }

    public String getUsernameFromToken(String jwtToken) throws CantParseJwtException{
        return getClaimFromToken(jwtToken, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws CantParseJwtException{
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) throws CantParseJwtException{
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch(SignatureException e){
            throw new CantParseJwtException("Cant parse token, double check!");
        }

    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) throws CantParseJwtException{
        final String username = getUsernameFromToken(jwtToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    public java.util.Date getExpirationDateFromToken(String token) throws CantParseJwtException{
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private String generateToken(Map<String, Object> claims, String subject) {
        Instant instant = Instant.now();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(instant.plus(TOKEN_EXPIRATION_MIN, ChronoUnit.MINUTES)))
                .signWith(getKey())
                .compact();
    }

    private Boolean isTokenExpired(String token) throws CantParseJwtException{
        Instant instant = Instant.now();
        final java.util.Date expiration = getExpirationDateFromToken(token);
        return expiration.before(Date.from(instant));
    }

    private Key getKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
