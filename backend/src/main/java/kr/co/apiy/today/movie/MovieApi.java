package kr.co.apiy.today.movie;

import kr.co.apiy.global.utils.ApiRequest;
import kr.co.apiy.global.utils.JsonUtils;
import kr.co.apiy.today.dto.MovieRankApiResult;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Component
@Log4j2
public class MovieApi {

    private final ApiRequest apiRequest;
    private final JsonUtils jsonUtils;

    private final String MOVIE_RANK_KEY;

    @Autowired
    public MovieApi(
            ApiRequest apiRequest,
            JsonUtils jsonUtils,
            @Value("${movie.rank.key}") String MOVIE_RANK_KEY
    ) {

        this.MOVIE_RANK_KEY = MOVIE_RANK_KEY;
        this.apiRequest = apiRequest;
        this.jsonUtils = jsonUtils;
    }

    public List<MovieRankApiResult> getMovieRankData(LocalDate targetDate) {
        log.info("===============================================");
        log.info("Movie Api getMovieRankData");
        final String BASE_URL = "http://www.kobis.or.kr";
        final String SUB_URL = "/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
        Map<String, String> queryParam = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String targetDt = targetDate.format(formatter);

        queryParam.put("key", MOVIE_RANK_KEY);
        queryParam.put("targetDt", targetDt);

        String response = apiRequest.get(BASE_URL, SUB_URL, queryParam);
        log.info("===============================================");
        log.info("response: " + response);

        JSONObject jsonObject = new JSONObject(response);
        JSONArray dailyBoxOfficeListJson = jsonObject.getJSONObject("boxOfficeResult").getJSONArray("dailyBoxOfficeList");
        List<MovieRankApiResult> movieRankApiResults = new ArrayList<>();
        IntStream.range(0, dailyBoxOfficeListJson.length()).forEach(index -> {
            JSONObject boxOfficeMovieJsonObj = dailyBoxOfficeListJson.getJSONObject(index);
            MovieRankApiResult movieRankApiResult = jsonUtils.fromJson(boxOfficeMovieJsonObj, MovieRankApiResult.class);
            movieRankApiResults.add(movieRankApiResult);
        });

        return movieRankApiResults;
    }

}
