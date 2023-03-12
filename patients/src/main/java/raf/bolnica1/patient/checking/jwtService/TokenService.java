package raf.bolnica1.patient.checking.jwtService;

import io.jsonwebtoken.Claims;

public interface TokenService {

    Claims parseToken(String token);

}
