package kr.co.apiy.today.weather;

import kr.co.apiy.global.exception.InternalServerException;
import kr.co.apiy.global.utils.ApiRequest;
import kr.co.apiy.global.utils.Constants;
import kr.co.apiy.global.utils.JsonUtils;
import kr.co.apiy.global.utils.StringUtils;
import kr.co.apiy.today.dto.WeatherApiResult;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
public class WeatherApi {

    private final ApiRequest apiRequest;
    private final JsonUtils jsonUtils;
    private final String WEATHER_FORECAST_KEY;

    @Autowired
    public WeatherApi(
            ApiRequest apiRequest,
            JsonUtils jsonUtils,
            @Value("${weather.forecast.key}") String WEATHER_FORECAST_KEY
    ) {
        this.apiRequest = apiRequest;
        this.jsonUtils = jsonUtils;
        this.WEATHER_FORECAST_KEY =  WEATHER_FORECAST_KEY;
    }

    public List<WeatherApiResult> getWeatherForecast() {
        log.info("===============================================");
        log.info("Weather Api checkWeather");

        final String BASE_URL = "http://apis.data.go.kr";
        final String SUB_URL = "/1360000/VilageFcstInfoService_2.0/getVilageFcst";

        Map<String, String> queryParam = getRequestQueryParam();

        String responseJson = apiRequest.get(BASE_URL, SUB_URL, queryParam);
        log.info("Weather Api response: {}", responseJson);
        JSONObject responseJsonObj = new JSONObject(responseJson).getJSONObject("response");
        JSONObject header = responseJsonObj.getJSONObject("header");

        String resultCode = header.getString("resultCode");
        boolean isSuccess = resultCode.equals("00");

        if (isSuccess) {
            JSONArray itemsJsonArray = responseJsonObj.getJSONObject("body").getJSONObject("items").getJSONArray("item");
            return jsonUtils.fromJsonArray(itemsJsonArray, WeatherApiResult.class);
        } else{
            throw new InternalServerException("기상청 Api 요청에 실패하였습니다.: " + System.lineSeparator() + responseJson);
        }

    }

    private Map<String, String> getRequestQueryParam() {
        LocalDateTime today = LocalDateTime.from(LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE_OF_SEOUL)));
        String[] baseDateTime = StringUtils.LocalDateToFormat(today, "yyyyMMdd HH00").split(" ");
        String baseDate = baseDateTime[0];
        String baseTime = baseDateTime[1];

        log.info("===============================================");
        log.info("기상청 Api 실행: {}, {}", baseDate, baseTime);
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("serviceKey", WEATHER_FORECAST_KEY);
        queryParam.put("pageNo", "1");
        queryParam.put("numOfRows", "100");
        queryParam.put("dataType", "JSON");
        queryParam.put("base_date", baseDate); // 발표일
        queryParam.put("base_time", baseTime); // 발표시간
        // 서울 기준
        queryParam.put("nx", "60");
        queryParam.put("ny", "127");
        return queryParam;
    }


}
