package raf.bolnica1.employees.checking.jwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenServiceImpl implements TokenService{
    @Value("${oauth.jwt.secret}")
    private String SECRET_KEY;

    public boolean isTokenExpired(String token){
        return parseToken(token).getExpiration().before(new Date());
    }

    public String generateToken(Claims claims){

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public Claims parseToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e) {
            return null;
        }
        return claims;
    }

    public boolean validateToken(String token, UserDetails user) {
        return (user.getUsername().equals(parseToken(token).get("username", String.class)) && !isTokenExpired(token));
    }
}
