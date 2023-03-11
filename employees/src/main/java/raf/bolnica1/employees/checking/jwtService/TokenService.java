package raf.bolnica1.employees.checking.jwtService;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import raf.bolnica1.employees.domain.Employee;

public interface TokenService {
    boolean isTokenExpired(String token);
    String generateToken(Claims claims);
    String generateToken(Employee employee);

    Claims parseToken(String token);

    boolean validateToken(String token, UserDetails user);
}
