package raf.bolnica1.employees.dto.token;

import lombok.Data;

@Data
public class TokenResponse {

    String message;

    public TokenResponse(String message) {
        this.message = message;
    }
}
