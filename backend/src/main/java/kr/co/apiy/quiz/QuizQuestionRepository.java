package kr.co.apiy.quiz;

import kr.co.apiy.quiz.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionRepository  extends JpaRepository<QuizQuestion, Long> {
}
