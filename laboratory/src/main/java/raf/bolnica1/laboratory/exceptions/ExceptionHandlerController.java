package raf.bolnica1.laboratory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raf.bolnica1.laboratory.dto.response.ErrorResponse;
import raf.bolnica1.laboratory.exceptions.employee.EmployeeAlreadyExistsException;
import raf.bolnica1.laboratory.exceptions.employee.EmployeeNotFoundException;
import raf.bolnica1.laboratory.exceptions.employee.EmployeePasswordException;
import raf.bolnica1.laboratory.util.JsonBuilder;

import java.sql.Date;
import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({
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

    private ErrorResponse getJson(Exception exception){
        Map<String, Object> json = new JsonBuilder()
                .put("timestamp", Date.from(Instant.now()).toString())
                .put("error", exception.getMessage())
                .build();
        return new ErrorResponse(json);
    }
}
