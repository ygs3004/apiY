package kr.co.apiy.today.movie;

import kr.co.apiy.today.entity.MovieRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieRankRepository extends JpaRepository<MovieRank, Long> {
    Optional<List<MovieRank>> findByRankDate(LocalDate rankDate);
}
