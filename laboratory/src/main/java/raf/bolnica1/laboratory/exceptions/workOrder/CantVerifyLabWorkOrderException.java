package raf.bolnica1.laboratory.exceptions.workOrder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CantVerifyLabWorkOrderException extends RuntimeException{
    public CantVerifyLabWorkOrderException(String message) {
        super(message);
    }

    public CantVerifyLabWorkOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
