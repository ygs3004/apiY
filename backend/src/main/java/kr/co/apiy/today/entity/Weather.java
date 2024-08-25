package kr.co.apiy.today.entity;

import jakarta.persistence.*;
import kr.co.apiy.global.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "WEATHER")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Weather extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("에상시간")
    @Column(unique = true)
    private LocalDateTime forecastDatetime;

    @Comment("강수확률(눈, 비 등)")
    private double precipitationProbability;

    @Comment("강수형태")
    private String precipitationType;

    @Comment("습도(%)")
    private double humidity;

    @Comment("하늘상태")
    private String skyCondition;

    @Comment("온도(℃)")
    private double temperature;

    @Comment("풍향(°)")
    private double windDirection;

    @Comment("풍속(m/s)")
    private double windSpeed;

}
