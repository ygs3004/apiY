package kr.co.apiy.global.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.apiy.global.exception.InternalServerException;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.StringJoiner;

@Component
@Log4j2
public class ApiRequest {

    private final WebClient webClient;

    public ApiRequest(){
        this.webClient = WebClient
                .builder()
                .defaultHeaders( httpHeaders -> {
                    httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                })
                .build();
    }

    @SneakyThrows
    public String get(String baseUri, String subUri, Map<String, String> queryParamsMap){
        return this.get(baseUri, subUri, queryParamsMap,  null);
    }

    @SneakyThrows
    public String get(String baseUri, String subUri, Map<String, String> queryParamsMap, Map<String, String> addHeaders) {
        String queryParamsStr = convertQueryParamsMapToString(queryParamsMap);
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(baseUri);
        // 공공 API 키 인코딩시 '+' 를 인코딩 하지 않음, uri builder Encoding NONE 설정 후 별도의 메서드에서 인코딩
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        return webClient
                .mutate()
                .baseUrl(baseUri)
                .uriBuilderFactory(uriBuilderFactory)
                .defaultHeaders(httpHeaders -> {
                    if (addHeaders != null) addHeaders.forEach(httpHeaders::set);
                }).build()
                .get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder.path(subUri).query(queryParamsStr).build();
                    log.info("Request Get URI: {}", uri);
                    return uri;
                })
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .doOnError(log::warn)
                .block();
    }

    public static String convertQueryParamsMapToString(Map<String, String> map) {
        StringJoiner joiner = new StringJoiner("&");

        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
                String value = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8);
                joiner.add(key + "=" + value);
            }
        } catch (Exception e) {
            throw new InternalServerException(e.getMessage());
        }

        return joiner.toString();
    }

    public <T> String post(String baseUri, String subUri, Map<String, String> headers, T requestBody){
        return webClient
                .mutate()
                .baseUrl(baseUri)
                .defaultHeaders(httpHeaders -> headers.forEach(httpHeaders::set))
                .build()
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(subUri)
                        .build())
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .doOnError(log::warn)
                .block();
    }

    public <T> String post(String baseUri, String subUri, T requestBody) {
        return this.webClient
                .mutate()
                .baseUrl(baseUri)
                .build()
                .post()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder.path(subUri).build();
                    log.info("Request Post URI: {}", uri);
                    return uri;
                })
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .doOnError(log::warn)
                .block();
    }

}
