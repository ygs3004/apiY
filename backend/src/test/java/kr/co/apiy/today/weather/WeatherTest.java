package kr.co.apiy.today.weather;

import kr.co.apiy.today.dto.WeatherApiResult;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@Log4j2
@ActiveProfiles("test")
public class WeatherTest {

    @Autowired
    WeatherApi weatherApi;
    @Autowired
    private WeatherService weatherService;

    @Test
    @Commit
    public void searchWeatherForecastTest() {
        List<WeatherApiResult> response = weatherApi.getWeatherForecast();
        weatherService.updateWeatherForecastData(response);
        log.info(weatherService.searchWeatherForecast());
    }

}
