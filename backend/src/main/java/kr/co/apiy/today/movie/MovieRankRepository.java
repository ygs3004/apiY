package kr.co.apiy.today.movie;

import kr.co.apiy.auth.entity.MemberEntity;
import kr.co.apiy.today.entity.MovieRankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieRankRepository extends JpaRepository<MovieRankEntity, Long> {
    Optional<List<MovieRankEntity>> findByRankDate(LocalDate rankDate);
}
