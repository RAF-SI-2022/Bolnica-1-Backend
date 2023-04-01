package raf.bolnica1.infirmary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raf.bolnica1.infirmary.dto.ErrorResponse;
import raf.bolnica1.infirmary.util.JsonBuilder;

import java.sql.Date;
import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

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
