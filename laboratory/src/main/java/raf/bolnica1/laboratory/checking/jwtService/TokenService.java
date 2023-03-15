package raf.bolnica1.laboratory.checking.jwtService;

import io.jsonwebtoken.Claims;

public interface TokenService {

    Claims parseToken(String token);

}
