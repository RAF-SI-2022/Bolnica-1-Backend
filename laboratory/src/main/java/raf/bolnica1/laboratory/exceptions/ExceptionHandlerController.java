package raf.bolnica1.laboratory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raf.bolnica1.laboratory.dto.response.ErrorResponse;
import raf.bolnica1.laboratory.exceptions.examinations.ExaminationNotFoundException;
import raf.bolnica1.laboratory.exceptions.parameterAnalysisResult.NoParameterAnalysisResultsForWorkOrder;
import raf.bolnica1.laboratory.exceptions.workOrder.CantVerifyLabWorkOrderException;
import raf.bolnica1.laboratory.exceptions.workOrder.LabWorkOrderNotFoundException;
import raf.bolnica1.laboratory.exceptions.workOrder.NoParameterAnalysisResultFound;
import raf.bolnica1.laboratory.exceptions.workOrder.NotAuthenticatedException;
import raf.bolnica1.laboratory.util.JsonBuilder;

import java.sql.Date;
import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({
            NoParameterAnalysisResultsForWorkOrder.class,
            CantVerifyLabWorkOrderException.class,
            LabWorkOrderNotFoundException.class,
            NoParameterAnalysisResultFound.class,
            NotAuthenticatedException.class,
            ExaminationNotFoundException.class,
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
