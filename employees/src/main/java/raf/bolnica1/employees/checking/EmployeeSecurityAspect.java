package raf.bolnica1.employees.checking;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import raf.bolnica1.employees.checking.jwtService.TokenService;

import java.lang.reflect.Method;

@Aspect
@Configuration
public class EmployeeSecurityAspect {

    @Value("${oauth.jwt.secret}")
    private String jwtSecret;

    private TokenService tokenService;
    private ObjectMapper objectMapper;

    public EmployeeSecurityAspect(TokenService tokenService){
        this.tokenService = tokenService;
        this.objectMapper = new ObjectMapper();
    }

    @Around("@annotation(raf.bolnica1.employees.checking.CheckEmployee)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //Get method signature
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //Check for authorization parameter
        String token = null;
        String forChangeLbz = null;
        for (int i = 0; i < methodSignature.getParameterNames().length; i++) {
            if (methodSignature.getParameterNames()[i].equals("authorization")) {
                if (joinPoint.getArgs()[i].toString().startsWith("Bearer"))
                    token = joinPoint.getArgs()[i].toString().split(" ")[1];
            }
            if(methodSignature.getParameterNames()[i].equals("lbz")){
                forChangeLbz = joinPoint.getArgs()[i].toString();
            }
        }
        //If token is not presents return UNAUTHORIZED response
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if(forChangeLbz == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Try to parse token
        Claims claims = tokenService.parseToken(token);
        //If fails return UNAUTHORIZED response
        if (claims == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        CheckEmployee checkEmployee = method.getAnnotation(CheckEmployee.class);

        boolean found = false;
        String changerLbz = new String(claims.get("lbz", String.class));

        if (changerLbz.equals(forChangeLbz)) {
            return joinPoint.proceed();
        }
        //Return FORBIDDEN if user has't appropriate role for specified route
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
