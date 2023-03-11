package raf.bolnica1.employees.communication.response;

import lombok.Data;

@Data
public class ResponseMessage {

    String message;

    public ResponseMessage(String message) {
        this.message = message;
    }
}
