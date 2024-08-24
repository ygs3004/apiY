package kr.co.apiy.global.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.apiy.global.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JsonUtils {

    private final ObjectMapper objectMapper;

    public <T> T fromJson(String json, Class<T> clazz) {
        T result;
        try {
            result = objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(e.getMessage());
        }
        return result;
    }

    public <T> T fromJsonObject(JSONObject jsonObj, Class<T> clazz) {
        T result;
        try {
            result = objectMapper.readValue(jsonObj.toString(), clazz);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(e.getMessage());
        }
        return result;
    }

    public <T> List<T> fromJsonArray(JSONArray jsonArray, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            T item = fromJsonObject(jsonArray.getJSONObject(i), clazz);
            result.add(item);
        }
        return result;
    }
}
