package kr.co.apiy.today.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(name = "날씨 정보")
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WeatherResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd EEEE HH시", locale = "ko")
    private LocalDateTime forecastDatetime;

    private double precipitationProbability;

    private String precipitationType;

    private double humidity;

    private String skyCondition;

    private double temperature;

    private double windDirection;

    private double windSpeed;

}
