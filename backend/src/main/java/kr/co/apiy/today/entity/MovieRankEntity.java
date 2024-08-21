package kr.co.apiy.today.entity;

import jakarta.persistence.*;
import kr.co.apiy.global.entity.BaseEntity;
import kr.co.apiy.global.converters.Converters;
import kr.co.apiy.today.dto.RankInto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "MOVIE_RANK", uniqueConstraints = {
        @UniqueConstraint(
                name = "RANK_DATE_UNIQUE",
                columnNames = {"rank", "rankDate"}
        )
})
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRankEntity  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String movieName;

    @Column(nullable = false)
    private LocalDate rankDate;

    @Column(nullable = false)
    private int rank;

    private int rankChange;

    @Convert(converter = Converters.ForRankInto.class)
    RankInto rankOldAndNew;

    LocalDate openDate;

    private long audienceTotalCnt; // 누적관객수를 출력합니다.

    private long audienceDayCnt; // 해당일의 관객수를 출력합니다.

    private long audienceChange; // 전일 대비 관객수 증감분을 출력합니다.

    private double audienceChangeRatio; // 전일 대비 관객수 증감 비율을 출력합니다.

}
