package kr.co.apiy.today.movie;

import kr.co.apiy.global.utils.Constants;
import kr.co.apiy.today.dto.MovieRankApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class MovieRankScheduler {

    private final MovieRankApi movieRankApi;
    private final MovieRankService movieRankService;

    // 초 분 시간 일 월 요일
    // 매일 자정에 실행: 0 0 0 * * ?
    // 매일 오전 9시 실행: 0 0 9 * * ?
    // 매주 월요일 오전 9시 실행: 0 0 9 ? * MON
    // 매월 1일 자정 실행: 0 0 0 1 * ?
    // 매시간 30분마다 실행: 0 30 * * * ?
    @Scheduled(cron = "0 5 0 * * ?")
    public void updateMovieRankData() {
        LocalDate today = LocalDate.from(LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE_OF_SEOUL)));
        LocalDate targetDate = today.minusDays(1);

        List<MovieRankApiResult> movieRanks = movieRankApi.getMovieRankData(targetDate);
        movieRankService.saveMovieRankData(movieRanks, targetDate);
    }

}
