package kr.co.apiy.today.movie;

import kr.co.apiy.global.utils.ApiRequest;
import kr.co.apiy.global.utils.JsonUtils;
import kr.co.apiy.today.dto.NewsApiResponse;
import kr.co.apiy.today.news.NewsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
@Log4j2
public class MovieScheduler {

    private final ApiRequest apiRequest;
    private final JsonUtils jsonUtils;

    private final String MOVIE_RANK_KEY;

    @Autowired
    public MovieScheduler(
            ApiRequest apiRequest,
            JsonUtils jsonUtils,
            @Value("${movie.rank.key}") String MOVIE_RANK_KEY
    ) {

        this.MOVIE_RANK_KEY = MOVIE_RANK_KEY;
        this.apiRequest = apiRequest;
        this.jsonUtils = jsonUtils;
    }

    // @Scheduled(cron = "0 0 0 * * ?")
    // public void updateMovieData() {
    //     String baseUrl = "http://www.kobis.or.kr";
    //     String subUrl = "/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
    //     Map<String, String> queryParam = new HashMap<>();
    //
    //     LocalDateTime today = LocalDateTime.now();
    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyymmdd");
    //     String targetDt = today.format(formatter);
    //
    //     queryParam.put("key", MOVIE_RANK_KEY);
    //     queryParam.put("targetDt", targetDt);
    //
    //     String response = apiRequest.get(baseUrl, subUrl, queryParam);
    //     log.info(response);
    // }

}
