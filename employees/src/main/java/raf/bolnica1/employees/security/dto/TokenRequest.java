package raf.bolnica1.employees.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {
    public String username;
    public String password;
}
