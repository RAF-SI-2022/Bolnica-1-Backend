package raf.bolnica1.employees.security.dto;

import lombok.Data;

@Data
public class TokenResponse {

    String message;

    public TokenResponse(String message) {
        this.message = message;
    }
}
