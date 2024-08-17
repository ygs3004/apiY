package kr.co.apiy.today.movie;

import kr.co.apiy.global.Constants;
import kr.co.apiy.global.utils.ApiRequest;
import kr.co.apiy.global.utils.JsonUtils;
import kr.co.apiy.today.dto.MovieRankApiResult;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public List<MovieRankApiResult> getMovieRankData() {
        log.info("===============================================");
        log.info("Movie Api getMovieRankData");
        String baseUrl = "http://www.kobis.or.kr";
        String subUrl = "/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
        Map<String, String> queryParam = new HashMap<>();

        LocalDateTime today = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE_OF_SEOUL));
        LocalDateTime yesterday = today.minusDays(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String targetDt = yesterday.format(formatter);

        queryParam.put("key", MOVIE_RANK_KEY);
        queryParam.put("targetDt", targetDt);

        String response = apiRequest.get(baseUrl, subUrl, queryParam);
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
