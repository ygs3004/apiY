package kr.co.apiy.quiz.repository;

import kr.co.apiy.quiz.entity.QuizSolveHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizSolveHistoryRepository extends JpaRepository<QuizSolveHistory, Long> {
}
