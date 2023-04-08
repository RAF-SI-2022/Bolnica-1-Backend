package raf.bolnica1.infirmary.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ErrorResponse {
    private String timestamp;
    private String error;

    public ErrorResponse(Map<String, Object> json) {
        this.timestamp = json.get("timestamp").toString();
        this.error = json.get("error").toString();
    }

}
