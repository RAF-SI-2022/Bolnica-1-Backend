package raf.bolnica1.employees.security.dto_dep;


import lombok.Data;

@Data
public class TokenResponse {

    private String message;

    public TokenResponse(String message) {
        this.message = message;
    }
}
