package kr.co.apiy.today.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieRank {

    private LocalDate rankDate;

    private int rank;

    private String movieName;

    private int rankChange;

    RankInto rankOldAndNew;

    LocalDate openDate;

    private long audienceTotalCnt;

    private long audienceDayCnt;

    private long audienceChange;

    private double audienceChangeRatio;

}
