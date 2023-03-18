package raf.bolnica1.employees.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonBuilder {

    private final Map<String, Object> data;

    public JsonBuilder() {
        data = new LinkedHashMap<>();
    }

    public JsonBuilder put(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return data;
    }

}

