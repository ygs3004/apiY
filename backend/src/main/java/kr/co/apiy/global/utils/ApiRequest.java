package kr.co.apiy.global.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.aspectj.apache.bcel.classfile.annotation.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.StringJoiner;

@Component
@Log4j2
public class ApiRequest {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public ApiRequest(ObjectMapper objectMapper){
        this.webClient = WebClient
                .builder()
                .defaultHeaders( httpHeaders -> {
                    httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                })
                .build();
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public String get(String baseUri, String subUri, Map<String, String> queryParamsMap){
        return this.get(baseUri, subUri, queryParamsMap,  null);
    }

    @SneakyThrows
    public String get(String baseUri, String subUri, Map<String, String> queryParamsMap, Map<String, String> addHeaders){
        String queryParamsStr = convertQueryParamsMapToString(queryParamsMap);
        return webClient
                .mutate()
                .baseUrl(baseUri)
                .defaultHeaders(httpHeaders -> {
                    if(addHeaders != null) addHeaders.forEach(httpHeaders::set);
                }).build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(subUri)
                        .query(queryParamsStr)
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(log::warn)
                .block();
    }

    public static String convertQueryParamsMapToString(Map<String, String> map) {
        StringJoiner joiner = new StringJoiner("&");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String encodedKey = "";
            String encodedValue = "";
            try {
                encodedKey = URLEncoder.encode(entry.getKey(), "UTF-8");
                encodedValue = URLEncoder.encode(entry.getValue(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            joiner.add(encodedKey + "=" + encodedValue);
        }

        return joiner.toString();
    }

    public <T> String post(String baseUri, String subUri, Map<String, String> headers, T requestBody){
        return webClient
                .mutate()
                .baseUrl(baseUri)
                .defaultHeaders(httpHeaders -> {
                    headers.forEach(httpHeaders::set);
                }).build()
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(subUri)
                        .build())
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(log::warn)
                .block();
    }

    public <T> String post(String baseUri, String subUri, T requestBody) {
        return this.webClient
                .mutate()
                .baseUrl(baseUri)
                .build()
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(subUri)
                        .build())
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(log::warn)
                .block();
    }

}
