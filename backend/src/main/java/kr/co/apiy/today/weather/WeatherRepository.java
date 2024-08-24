package kr.co.apiy.today.weather;

import kr.co.apiy.today.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
    Optional<WeatherEntity> findAllByForecastDatetime(LocalDateTime forecastDatetime);
}
