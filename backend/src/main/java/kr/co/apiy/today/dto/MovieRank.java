package kr.co.apiy.today.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieRank {

    private String movieName;

    private LocalDateTime rankDate;

    private int rank;

    private int rankChange;

    RankInto rankOldAndNew;

    LocalDateTime openDate;

    private long audienceTotalCnt;

    private long audienceDayCnt;

    private long audienceChange;

    private double audienceChangeRatio;

}
