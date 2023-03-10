package raf.bolnica1.employees.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Implementirati po principu ovakve klase koju smo radili na NWP-u
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //Ovde implementirati logiku i zameniti Object object konkretnim request wrapper-om
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody Object object){
        return null;
    }
}
