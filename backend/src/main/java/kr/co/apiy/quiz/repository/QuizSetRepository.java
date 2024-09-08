package kr.co.apiy.quiz.repository;

import kr.co.apiy.quiz.entity.QuizSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizSetRepository extends JpaRepository<QuizSet, Long> {
}
