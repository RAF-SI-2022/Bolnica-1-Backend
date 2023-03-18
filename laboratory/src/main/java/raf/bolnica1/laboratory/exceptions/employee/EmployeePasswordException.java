package raf.bolnica1.laboratory.exceptions.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmployeePasswordException extends RuntimeException{

    public EmployeePasswordException(String message) {
        super(message);
    }

    public EmployeePasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
