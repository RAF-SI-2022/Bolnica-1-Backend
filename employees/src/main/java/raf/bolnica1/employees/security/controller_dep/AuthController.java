package raf.bolnica1.employees.security.controller_dep;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.bolnica1.employees.security.dto_dep.TokenRequest;
import raf.bolnica1.employees.security.dto_dep.TokenResponse;
import raf.bolnica1.employees.security.service_dep.SecurityService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final SecurityService securityService;

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody @NotNull TokenRequest tokenRequest) {
        authenticate(tokenRequest.username, tokenRequest.password);
        String token = securityService.generateToken(tokenRequest.getUsername());
        return new ResponseEntity<>(new TokenResponse(token), HttpStatus.OK);
    }

    private void authenticate(@NotNull @NotBlank String username, @NotNull @NotBlank String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new BadCredentialsException("Wrong username or password.", e);
        }
    }

}

