package raf.bolnica1.employees.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raf.bolnica1.employees.dto.token.TokenResponse;
import raf.bolnica1.employees.exceptions.department.DepartmentNotFoundException;
import raf.bolnica1.employees.exceptions.employee.EmployeeAlreadyExistsException;
import raf.bolnica1.employees.exceptions.employee.EmployeeNotFoundException;
import raf.bolnica1.employees.exceptions.employee.EmployeePasswordException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DepartmentNotFoundException.class, EmployeeNotFoundException.class, EmployeePasswordException.class, EmployeeAlreadyExistsException.class})
    public TokenResponse handleNullPointerException(Exception exception) {
        return new TokenResponse(exception.getMessage());
    }

}
