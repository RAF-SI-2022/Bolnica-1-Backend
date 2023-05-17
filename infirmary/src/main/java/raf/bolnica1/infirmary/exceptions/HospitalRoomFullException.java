package raf.bolnica1.infirmary.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HospitalRoomFullException extends RuntimeException{

    public HospitalRoomFullException(String message) {
        super(message);
    }

    public HospitalRoomFullException(String message, Throwable cause) {
        super(message, cause);
    }

}

