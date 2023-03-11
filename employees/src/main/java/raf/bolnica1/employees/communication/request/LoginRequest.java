package raf.bolnica1.employees.communication.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    public String username;
    public String password;
}
