package kr.co.apiy.today.movie;

import kr.co.apiy.global.exception.InternalServerException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
public class MovieRankApi {

    private final ApiRequest apiRequest;
    private final JsonUtils jsonUtils;

    private final String MOVIE_RANK_KEY;

    @Autowired
    public MovieRankApi(
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
        log.info("MovieRank Api getMovieRankData");
        final String BASE_URL = "http://www.kobis.or.kr";
        final String SUB_URL = "/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
        Map<String, String> queryParam = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String targetDt = targetDate.format(formatter);

        queryParam.put("key", MOVIE_RANK_KEY);
        queryParam.put("targetDt", targetDt);
        JSONArray dailyBoxOfficeListJson;
        try {
            String response = apiRequest.get(BASE_URL, SUB_URL, queryParam);
            log.info("===============================================");
            log.info("response: " + response);

            JSONObject jsonObject = new JSONObject(response);
            dailyBoxOfficeListJson = jsonObject.getJSONObject("boxOfficeResult").getJSONArray("dailyBoxOfficeList");
        } catch (Exception e) {
            throw new InternalServerException("영화 순위 Api 요청에 실패하였습니다.");
        }

        return jsonUtils.fromJsonArray(dailyBoxOfficeListJson, MovieRankApiResult.class);
    }

}
