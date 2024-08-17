package kr.co.apiy.today.news;

import kr.co.apiy.global.utils.ApiRequest;
import kr.co.apiy.global.utils.JsonUtils;
import kr.co.apiy.today.dto.NewsApiResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class NewsApi {

    private final ApiRequest apiRequest;
    private final JsonUtils jsonUtils;
    private final String NAVER_CLIENT_ID;
    private final String NAVER_CLIENT_SECRET;

    @Autowired
    public NewsApi(
            ApiRequest apiRequest,
            JsonUtils jsonUtils,
            @Value("${naver.client.id}") String NAVER_CLIENT_ID,
            @Value("${naver.client.secret}") String NAVER_CLIENT_SECRET
    ) {
        this.apiRequest = apiRequest;
        this.jsonUtils = jsonUtils;
        this.NAVER_CLIENT_ID =  NAVER_CLIENT_ID;
        this.NAVER_CLIENT_SECRET = NAVER_CLIENT_SECRET;
    }

    public NewsApiResult getLatestNewsData() {
        log.info("===============================================");
        log.info("News Api getLatestNewsData");

        String baseUrl = "https://openapi.naver.com";
        String subUrl = "/v1/search/news.json";
        Map<String, String> header = new HashMap<>();
        header.put("X-Naver-Client-Id", NAVER_CLIENT_ID);
        header.put("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);
        Map<String, String> queryParam = new HashMap<>();
        String searchString = "IT";

        queryParam.put("query", searchString);
        queryParam.put("sort", "date");
        queryParam.put("display", "100");

        String response = apiRequest.get(baseUrl, subUrl, queryParam, header);

        return jsonUtils.fromJson(response, NewsApiResult.class);
    }


}
