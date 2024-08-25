package kr.co.apiy.today.weather;

import kr.co.apiy.today.dto.WeatherApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class WeatherScheduler {

    private final WeatherApi weatherApi;
    private final WeatherService weatherService;

    @Scheduled(cron = "0 30 2,5,8,11,14,17,20,23 * * ?") // 단기예보 발표 시간 0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300 (1일 8회), 10분에 업데이트
    public void updateWeatherData() {
        List<WeatherApiResult> response = weatherApi.getWeatherForecast();
        weatherService.updateWeatherForecastData(response);
    }
}
