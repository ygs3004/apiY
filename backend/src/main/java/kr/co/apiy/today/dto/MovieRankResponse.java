package kr.co.apiy.today.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.apiy.global.utils.Constants;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieRankResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.GLOBAL_DATE_FORMAT)
    private LocalDate rankDate;

    private int rank;

    private String movieName;

    private int rankChange;

    private String rankOldAndNew;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.GLOBAL_DATE_FORMAT)
    private LocalDate openDate;

    private long audienceTotalCnt;

    private long audienceDayCnt;

    private long audienceChange;

    private double audienceChangeRatio;

}
