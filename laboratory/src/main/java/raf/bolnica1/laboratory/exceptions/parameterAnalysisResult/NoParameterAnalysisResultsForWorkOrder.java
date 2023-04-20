package raf.bolnica1.laboratory.exceptions.parameterAnalysisResult;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoParameterAnalysisResultsForWorkOrder extends RuntimeException {
    public NoParameterAnalysisResultsForWorkOrder(String message) {
        super(message);
    }

    public NoParameterAnalysisResultsForWorkOrder(String message, Throwable cause) {
        super(message, cause);
    }
}
