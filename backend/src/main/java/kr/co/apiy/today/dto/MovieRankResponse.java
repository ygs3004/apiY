package kr.co.apiy.today.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.apiy.global.utils.Constants;
import lombok.*;

import java.time.LocalDate;

@Schema(name = "박스오피스 랭크 정보")
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieRankResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.GLOBAL_DATE_FORMAT)
    @Schema(description = "기준일")
    private LocalDate rankDate;

    @Schema(description = "순위")
    private int rank;

    @Schema(description = "영화이름")
    private String movieName;

    @Schema(description = "순위 변화량")
    private int rankChange;

    @Schema(description = "신규 진입정보")
    private String rankOldAndNew;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.GLOBAL_DATE_FORMAT)
    @Schema(description = "개봉일")
    private LocalDate openDate;

    @Schema(description = "총 관객수")
    private long audienceTotalCnt;

    @Schema(description = "일일 총 관객수")
    private long audienceDayCnt;

    @Schema(description = "일일 관객 변화")
    private long audienceChange;

    @Schema(description = "일일 관객 변화율")
    private double audienceChangeRatio;

}
