package raf.bolnica1.employees.controllers;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import raf.bolnica1.employees.checking.jwtService.TokenService;
import raf.bolnica1.employees.domain.Employee;
import raf.bolnica1.employees.dto.token.TokenRequest;
import raf.bolnica1.employees.dto.token.TokenResponse;
import raf.bolnica1.employees.repository.EmployeeRepository;
import raf.bolnica1.employees.services.EmployeeService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private EmployeeService employeeService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest tokenRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(), tokenRequest.getPassword()));
            Employee employee = employeeService.getUserByUsername(tokenRequest.getUsername());
            Claims claims = Jwts.claims();
            claims.put("id", employee.getId());
            claims.put("username", tokenRequest.getUsername());
            claims.put("lbz", employee.getLbz());
            return new ResponseEntity<TokenResponse>(new TokenResponse(tokenService.generateToken(claims)), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<TokenResponse>(new TokenResponse("Invalid username/password"), HttpStatus.BAD_REQUEST);
        }
    }
}
