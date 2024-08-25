package kr.co.apiy.today.weather;

import kr.co.apiy.today.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Optional<Weather> findAllByForecastDatetime(LocalDateTime forecastDatetime);
}
