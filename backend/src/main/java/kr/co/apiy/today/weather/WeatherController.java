package kr.co.apiy.today.weather;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.apiy.today.dto.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@Tag(name = "날씨 API", description = "날씨 예보 정보 확인")
@RequestMapping("/weather/*")
public class WeatherController {

    private final WeatherService weatherService;

    @Operation(summary = "날씨 예보", description = "기상청 API 단기예보정보로 서울 날씨를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = WeatherResponse.class)))
            }),
    })
    @GetMapping("/forecast")
    public ResponseEntity<List<WeatherResponse>> searchWeatherForecast() {
        return ResponseEntity.ok(weatherService.searchWeatherForecast());
    }

}
