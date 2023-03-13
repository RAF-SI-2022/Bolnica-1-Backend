package raf.bolnica1.patient.checking;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import raf.bolnica1.patient.checking.jwtService.TokenService;
import raf.bolnica1.patient.dto.permission.PermissionsCheckDto;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Configuration
public class EmployeeSecurityAspect {

    @Value("${oauth.jwt.secret}")
    private String jwtSecret;

    private TokenService tokenService;
    private ObjectMapper objectMapper;
    private RestTemplate userServiceRestTemplate;

    public EmployeeSecurityAspect(TokenService tokenService, RestTemplate userServiceRestTemplate){
        this.tokenService = tokenService;
        this.objectMapper = new ObjectMapper();
        this.userServiceRestTemplate = userServiceRestTemplate;
    }

    @Around("@annotation(raf.bolnica1.patient.checking.CheckPermission)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //Get method signature
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //Check for authorization parameter
        String token = null;

        for (int i = 0; i < methodSignature.getParameterNames().length; i++) {
            if (methodSignature.getParameterNames()[i].equals("authorization")) {
                if (joinPoint.getArgs()[i].toString().startsWith("Bearer")) {
                  token = joinPoint.getArgs()[i].toString().split(" ")[1];
                  break;
                }
            }
        }
        //If token is not presents return UNAUTHORIZED response
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        //Try to parse token
        Claims claims = tokenService.parseToken(token);
        //If fails return UNAUTHORIZED response
        if (claims == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        CheckPermission checkPermission = method.getAnnotation(CheckPermission.class);

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(token);

        HttpEntity<PermissionsCheckDto> request = new HttpEntity<>(new PermissionsCheckDto(Arrays.asList(checkPermission.permissions())), headers);
        String url = "/check_permission";

        ResponseEntity<Boolean> response = userServiceRestTemplate.exchange(url, HttpMethod.POST, request, Boolean.class);

        if (response.getBody()) {
            return joinPoint.proceed();
        }
        //Return FORBIDDEN if user has't appropriate role for specified route
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
