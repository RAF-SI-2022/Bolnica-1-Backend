package raf.bolnica1.employees.checking.jwtService;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {
    boolean isTokenExpired(String token);
    String generateToken(Claims claims);

    Claims parseToken(String token);

    boolean validateToken(String token, UserDetails user);
}
