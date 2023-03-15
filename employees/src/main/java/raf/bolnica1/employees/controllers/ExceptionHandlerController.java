package raf.bolnica1.employees.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import raf.bolnica1.employees.dto.response.ErrorResponse;
import raf.bolnica1.employees.exceptions.department.DepartmentNotFoundException;
import raf.bolnica1.employees.exceptions.employee.EmployeeAlreadyExistsException;
import raf.bolnica1.employees.exceptions.employee.EmployeeNotFoundException;
import raf.bolnica1.employees.exceptions.employee.EmployeePasswordException;
import raf.bolnica1.employees.util.JsonBuilder;

import java.sql.Date;
import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({
            DepartmentNotFoundException.class,
            EmployeeNotFoundException.class,
            EmployeePasswordException.class,
            EmployeeAlreadyExistsException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCustomExceptions(Exception exception) {
        Map<String, Object> json = new JsonBuilder()
                .put("timestamp", Date.from(Instant.now()).toString())
                .put("error", exception.getMessage())
                .build();
        return new ErrorResponse(json);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleOtherExceptions(Exception exception) {
        Map<String, Object> json = new JsonBuilder()
                .put("timestamp", Date.from(Instant.now()).toString())
                //.put("error", HttpStatus.BAD_REQUEST)
                .put("error", "This specific error is not handled yet. Check the data you are sending!")
                .build();
        return new ErrorResponse(json);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalExceptions(Exception exception) {
        Map<String, Object> json = new JsonBuilder()
                .put("timestamp", Date.from(Instant.now()).toString())
                //.put("error", exception.getLocalizedMessage())
                .put("error", "This specific error is not handled yet. Check the data you are sending!")
                .build();
        return new ErrorResponse(json);
    }

}
