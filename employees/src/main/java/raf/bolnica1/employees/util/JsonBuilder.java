package raf.bolnica1.employees.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class JsonBuilder {

    private final Map<String, Object> data;

    public JsonBuilder() {
        data = new HashMap<>();
    }

    public JsonBuilder put(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public String buildString()  {
        String json;
        try{
            ObjectMapper mapper = new ObjectMapper();
            json =  mapper.writeValueAsString(data);
        }catch (JsonProcessingException e){
            return "Error parsing json";
        }
        return json;
    }

    public Map<String, Object> build() {
        return data;
    }

}
