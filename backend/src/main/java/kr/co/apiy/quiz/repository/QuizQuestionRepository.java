package kr.co.apiy.quiz.repository;

import kr.co.apiy.quiz.entity.QuizQuestion;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizQuestionRepository  extends JpaRepository<QuizQuestion, Long> {

    @EntityGraph(attributePaths = {"quizSet"}) // fetch join eager
    Optional<List<QuizQuestion>> findQuizQuestionsByQuizSetIdOrderById(Long quizSetId);

}
