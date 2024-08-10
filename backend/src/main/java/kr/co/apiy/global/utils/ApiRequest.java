package kr.co.apiy.global.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ApiRequest {

    private final WebClient webClient;

    @Autowired
    public ApiRequest(ObjectMapper objectMapper){
        this.webClient = WebClient
                .builder()
                .defaultHeaders( httpHeaders -> {
                    httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                })
                .build();
    }

    public <T> JSONObject post(String baseUri, String subUri, T requestBody) {
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
                .bodyToMono(JSONObject.class)
                .block();
    }

}
