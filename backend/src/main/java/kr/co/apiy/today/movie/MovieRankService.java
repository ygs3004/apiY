package kr.co.apiy.today.movie;

import kr.co.apiy.global.exception.InternalServerException;
import kr.co.apiy.today.dto.MovieRankApiResult;
import kr.co.apiy.today.dto.MovieRankResponse;
import kr.co.apiy.today.dto.RankInto;
import kr.co.apiy.today.entity.MovieRankEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class MovieRankService {

    private final MovieRankRepository movieRankRepository;

    public List<MovieRankResponse> getYesterdayMovieRank() {
        PageRequest pageable = PageRequest.of(0, 10, Sort.by("rankDate").descending());
        Page<MovieRankEntity> movieRanksPage = movieRankRepository.findAll(pageable);
        List<MovieRankEntity> movieRankEntities = movieRanksPage.getContent();
        List<MovieRankResponse> movieRankResponseResult = movieRankEntities.stream()
                .map(movieRankEntity -> MovieRankResponse.builder()
                        .rankDate(movieRankEntity.getRankDate())
                        .rank(movieRankEntity.getRank())
                        .movieName(movieRankEntity.getMovieName())
                        .rankChange(movieRankEntity.getRankChange())
                        .rankOldAndNew(movieRankEntity.getRankOldAndNew().name())
                        .openDate(movieRankEntity.getOpenDate())
                        .audienceTotalCnt(movieRankEntity.getAudienceTotalCnt())
                        .audienceDayCnt(movieRankEntity.getAudienceDayCnt())
                        .audienceChange(movieRankEntity.getAudienceChange())
                        .audienceChangeRatio(movieRankEntity.getAudienceChangeRatio())
                        .build()
                ).toList();

        log.info("===============================================");
        log.info(movieRankResponseResult);
        return movieRankResponseResult;
    }

    public void saveMovieRankData(List<MovieRankApiResult> movieRanks, LocalDate targetDate) {
        Optional<List<MovieRankEntity>> existRankEntityList = movieRankRepository.findByRankDate(targetDate);
        // 데이터가 없을경우 List 길이가 0으로 return, isPresent = true
        AtomicBoolean isNotUpdateYet = new AtomicBoolean(false);
        existRankEntityList.ifPresentOrElse(
                rankEntities -> isNotUpdateYet.set(rankEntities.isEmpty()),
                () -> {
                    throw new InternalServerException("동일한 Movie Rank 정보(날짜) Update");
                }
        );

        if (isNotUpdateYet.get()) {
            List<MovieRankEntity> saveList = new ArrayList<>();
            movieRanks.forEach(movieRankApiResult -> {
                MovieRankEntity movieRankEntity = MovieRankEntity
                        .builder()
                        .rankDate(targetDate)
                        .rank(movieRankApiResult.getRank())
                        .movieName(movieRankApiResult.getMovieNm())
                        .rankChange(movieRankApiResult.getRankInten())
                        .rankOldAndNew(RankInto.valueOf(movieRankApiResult.getRankOldAndNew()))
                        .openDate(
                                LocalDate.parse(
                                        movieRankApiResult.getOpenDt()
                                        , DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                )
                        )
                        .audienceTotalCnt(movieRankApiResult.getAudiAcc())
                        .audienceDayCnt(movieRankApiResult.getAudiCnt())
                        .audienceChange(movieRankApiResult.getAudiInten())
                        .audienceChangeRatio(movieRankApiResult.getAudiChange())
                        .build();
                saveList.add(movieRankEntity);
            });
            movieRankRepository.saveAll(saveList);
        }
    }

}
