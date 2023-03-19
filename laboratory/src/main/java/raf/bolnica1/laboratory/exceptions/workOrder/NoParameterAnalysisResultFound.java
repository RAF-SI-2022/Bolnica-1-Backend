package raf.bolnica1.laboratory.exceptions.workOrder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoParameterAnalysisResultFound extends RuntimeException{
    public NoParameterAnalysisResultFound(String message) {
        super(message);
    }

    public NoParameterAnalysisResultFound(String message, Throwable cause) {
        super(message, cause);
    }
}
