package kr.co.apiy.global.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.apiy.global.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonUtils {

    private final ObjectMapper objectMapper;

    public <T> T fromJson(String json, Class<T> clazz) {
        T result = null;
        try {
            result = objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(e.getMessage());
        }
        return result;
    }

    public <T> T fromJson(JSONObject json, Class<T> clazz) {
        T result = null;
        try {
            result = objectMapper.readValue(json.toString(), clazz);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(e.getMessage());
        }
        return result;
    }
}
