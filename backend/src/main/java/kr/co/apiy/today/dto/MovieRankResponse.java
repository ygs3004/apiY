package kr.co.apiy.today.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieRankResponse {

    private String rankDate;

    private int rank;

    private String movieName;

    private int rankChange;

    private String rankOldAndNew;

    private String openDate;

    private long audienceTotalCnt;

    private long audienceDayCnt;

    private long audienceChange;

    private double audienceChangeRatio;

}
