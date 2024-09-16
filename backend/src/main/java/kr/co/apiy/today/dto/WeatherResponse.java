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
    @Schema(description = "예정시간")
    private LocalDateTime forecastDatetime;

    @Schema(description = "강수확률")
    private double precipitationProbability;

    @Schema(description = "강수종류(눈,비)")
    private String precipitationType;

    @Schema(description = "습도")
    private double humidity;

    @Schema(description = "하늘")
    private String skyCondition;

    @Schema(description = "온도(°C)")
    private double temperature;

    @Schema(description = "풍향(°)")
    private double windDirection;

    @Schema(description = "풍속(m/s)")
    private double windSpeed;

}
