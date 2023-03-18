package raf.bolnica1.employees.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raf.bolnica1.employees.dto.response.ErrorResponse;
import raf.bolnica1.employees.exceptionHandler.exceptions.department.DepartmentNotFoundException;
import raf.bolnica1.employees.exceptionHandler.exceptions.employee.EmployeeAlreadyExistsException;
import raf.bolnica1.employees.exceptionHandler.exceptions.employee.EmployeeNotFoundException;
import raf.bolnica1.employees.exceptionHandler.exceptions.employee.EmployeePasswordException;
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
        return getJson(exception);
    }

    @ExceptionHandler({
            Exception.class,
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherExceptions(Exception exception) {
        return getJson(exception);
    }

    private ErrorResponse getJson(Exception exception) {
        Map<String, Object> json = new JsonBuilder()
                .put("timestamp", Date.from(Instant.now()).toString())
                .put("error", exception.getMessage())
                .build();
        return new ErrorResponse(json);
    }

}
