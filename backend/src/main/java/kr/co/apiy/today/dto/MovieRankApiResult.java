package kr.co.apiy.today.dto;

import lombok.*;

@ToString
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRankApiResult {
    private int rnum; // 순번
    private int rank; // 순위
    private int rankInten; // 순위증감
    private String rankOldAndNew; // 신규진입여부, OLD, NEW
    private String movieCd; // 대표코드?
    private String movieNm; // 영화명(국문)
    private String openDt; // 개봉일
    private long salesAmt; // 해당일의 매출액을 출력합니다.
    private double salesShare; // 해당일자 상영작의 매출총액 대비 해당 영화의 매출비율을 출력합니다.
    private long salesInten; // 전일 대비 매출액 증감 분을 출력합니다.
    private double salesChange; // 전일 대비 매출액 증감 비율을 출력합니다.
    private long salesAcc; // 누적매출액을 출력합니다.
    private long audiCnt; // 해당일의 관객수를 출력합니다.
    private long audiInten; // 전일 대비 관객수 증감분을 출력합니다.
    private double audiChange; // 전일 대비 관객수 증감 비율을 출력합니다.
    private long audiAcc; // 누적관객수를 출력합니다.
    private int scrnCnt; // 해당일자에 상영한 스크린수를 출력합니다.
    private int showCnt; // 해당일자에 상영된 횟수를 출력합니다.
}
