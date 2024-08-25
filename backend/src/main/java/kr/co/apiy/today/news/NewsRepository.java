package kr.co.apiy.today.news;

import kr.co.apiy.today.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findByTitleLike(String title);
}
