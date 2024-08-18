package kr.co.apiy.today.movie;

import kr.co.apiy.global.Constants;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.ZoneId;

@SpringBootTest
@Log4j2
@ActiveProfiles("test")
public class MovieTest {

    @Autowired
    MovieRankService movieRankService;

    @Autowired
    private MovieRankScheduler movieRankScheduler;

    @Test
    public void getMovieRankTest() {
        // LocalDateTime today = LocalDateTime.now(ZoneId.of(Constants.TIME_ZONE_OF_SEOUL));
        // LocalDateTime targetDate = today.minusDays(1);
        movieRankScheduler.updateMovieRankData();
        log.info(movieRankService.getYesterdayMovieRank());
    }

}
