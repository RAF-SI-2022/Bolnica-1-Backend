package raf.bolnica1.laboratory.exceptions.workOrder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LabWorkOrderNotFoundException extends RuntimeException{
    public LabWorkOrderNotFoundException(String message) {
        super(message);
    }

    public LabWorkOrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
