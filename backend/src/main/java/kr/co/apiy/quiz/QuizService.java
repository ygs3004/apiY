package kr.co.apiy.quiz;

import jakarta.validation.Valid;
import kr.co.apiy.quiz.dto.request.QuizQuestionSaveRequest;
import kr.co.apiy.quiz.dto.request.QuizSetSaveRequest;
import kr.co.apiy.quiz.dto.response.QuizSetResponse;
import kr.co.apiy.quiz.entity.QuizAnswer;
import kr.co.apiy.quiz.entity.QuizQuestion;
import kr.co.apiy.quiz.entity.QuizSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class QuizService {

    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizSetRepository quizSetRepository;

    public List<QuizSetResponse> getQuizSets(int page) {
        PageRequest pageable = PageRequest.of(page, 10);
        return quizSetRepository
                .findAll(pageable)
                .stream()
                .map(quizSet -> QuizSetResponse
                        .builder()
                        .category(quizSet.getCategory())
                        .subject(quizSet.getSubject())
                        .build())
                .toList();
    }

    public void saveQuizSet(@Valid QuizSetSaveRequest quizSetSaveRequest) {
        QuizSet quizSetEntity = QuizSet.builder()
                .subject(quizSetSaveRequest.getSubject())
                .category(quizSetSaveRequest.getCategory())
                .build();

        List<QuizQuestion> saveQuestions = new ArrayList<>();

        quizSetSaveRequest.getQuestions().forEach(quizQuestionSaveRequest -> {

            QuizQuestion quizQuestionEntity = QuizQuestion.builder()
                    .quizSet(quizSetEntity)
                    .question(quizQuestionSaveRequest.getQuestion())
                    .build();

            quizQuestionSaveRequest
                    .getAnswers()
                    .forEach(quizAnswerSaveRequest -> {
                        QuizAnswer quizAnswerEntity = QuizAnswer.builder()
                                .answer(quizAnswerSaveRequest.getAnswer())
                                .isCorrect(quizAnswerSaveRequest.getIsCorrect())
                                .build();
                        quizQuestionEntity.addAnswer(quizAnswerEntity);
                    });

            saveQuestions.add(quizQuestionEntity);
        });

        quizQuestionRepository.saveAll(saveQuestions);
    }

}
