package kr.co.apiy.today.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class WeatherApiResult {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    LocalDate baseDate; // 기준일

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HHmm")
    LocalTime baseTime; // 기준시각

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    LocalDate fcstDate; // 예보일

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HHmm")
    LocalTime fcstTime; // 예보시각

    String category; // 값 종류

    String fcstValue; // 값

    int nx; // 위치 격자 x

    int ny; // 위치 격자 y

}
