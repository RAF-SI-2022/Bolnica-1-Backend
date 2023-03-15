package raf.bolnica1.laboratory.checking.jwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenServiceImpl implements TokenService{
    @Value("${oauth.jwt.secret}")
    private String SECRET_KEY;

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

}
