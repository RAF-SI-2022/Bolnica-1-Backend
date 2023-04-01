package raf.bolnica1.infirmary.exceptions.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CantParseJwtException extends RuntimeException{

    public CantParseJwtException(String message) {
        super(message);
    }

    public CantParseJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
