package kr.co.apiy.today.weather;

import kr.co.apiy.today.dto.WeatherApiResult;
import kr.co.apiy.today.dto.WeatherResponse;
import kr.co.apiy.today.entity.WeatherEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;

    public void updateWeatherForecastData(List<WeatherApiResult> WeatherApiResult) {
        Map<LocalDateTime, WeatherEntity> forecastEntitiesPerTime = parseWeatherApiResult(WeatherApiResult);
        List<WeatherEntity> savedEntities = new ArrayList<>();
        forecastEntitiesPerTime.forEach((localDateTime, forecastEntity) -> {
            weatherRepository
                    .findAllByForecastDatetime(localDateTime)
                    .ifPresent(findForecastEntity -> forecastEntity.setId(findForecastEntity.getId()));

            savedEntities.add(forecastEntity);
        });

        weatherRepository.saveAll(savedEntities);
    }

    public Map<LocalDateTime, WeatherEntity> parseWeatherApiResult(List<WeatherApiResult> WeatherApiResult) {
        Map<LocalDateTime, WeatherEntity> timeMap = new HashMap<>();
        for (WeatherApiResult item : WeatherApiResult) {
            LocalDateTime fcstDatetime = LocalDateTime.of(item.getFcstDate(), item.getFcstTime());
            WeatherEntity foreCastTimeEntity = timeMap.computeIfAbsent(fcstDatetime,
                    timeKey_ -> WeatherEntity
                            .builder()
                            .forecastDatetime(fcstDatetime)
                            .build());

            String value = item.getFcstValue();
            switch (item.getCategory()) {
                case "POP" -> {
                    // POP	강수확률	%	8
                    double precipitationProbability = Double.parseDouble(value);
                    foreCastTimeEntity.setPrecipitationProbability(precipitationProbability);
                }
                case "PTY" -> {
                    // 강수형태(PTY) 코드 : (단기) 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)
                    int precipitationTypeCode = Integer.parseInt(value);
                    String precipitationType = this.convertPrecipitationType(precipitationTypeCode);
                    foreCastTimeEntity.setPrecipitationType(precipitationType);
                }
                case "REH" -> {
                    // REH	습도	%	8
                    double humidity = Double.parseDouble(value);
                    foreCastTimeEntity.setHumidity(humidity);
                }
                case "SKY" -> {
                    // 하늘상태(SKY) 코드 : 맑음(1), 구름많음(3), 흐림(4)
                    int skyConditionCode = Integer.parseInt(value);
                    String skyCondition = convertSkyCondition(skyConditionCode);
                    foreCastTimeEntity.setSkyCondition(skyCondition);
                }
                case "TMP" -> {
                    // TMP	기온	℃	10
                    double temperature = Double.parseDouble(value);
                    foreCastTimeEntity.setTemperature(temperature);
                }
                case "VEC" -> {
                    // VEC	풍향	deg	10
                    double windDirection = Double.parseDouble(value);
                    foreCastTimeEntity.setWindDirection(windDirection);
                }
                case "WSD" -> {
                    double windSpeed = Double.parseDouble(value);
                    foreCastTimeEntity.setWindSpeed(windSpeed);
                }
            }
        }

        return timeMap;
    }

    public String convertPrecipitationType(int code) {
        // 강수형태(PTY) 코드 : (단기) 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)
        return switch (code) {
            case 1 -> "비";
            case 2 -> "비/눈";
            case 3 -> "눈";
            case 4 -> "소나기";
            default -> "";
        };
    }

    public String convertSkyCondition(int code) {
        // 하늘상태(SKY) 코드 : 맑음(1), 구름많음(3), 흐림(4)
        return switch (code) {
            case 1 -> "맑음";
            case 3 -> "구름많음";
            case 4 -> "흐림";
            default -> "";
        };
    }

    public List<WeatherResponse> searchWeatherForecast() {
        return null;
    }

}
