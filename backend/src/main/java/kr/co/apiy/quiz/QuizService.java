package kr.co.apiy.quiz;

import jakarta.validation.Valid;
import kr.co.apiy.global.exception.InternalServerException;
import kr.co.apiy.quiz.dto.request.QuizSetSaveRequest;
import kr.co.apiy.quiz.dto.response.QuizAnswerResponse;
import kr.co.apiy.quiz.dto.response.QuizQuestionResponse;
import kr.co.apiy.quiz.dto.response.QuizSetResponse;
import kr.co.apiy.quiz.entity.QuizAnswer;
import kr.co.apiy.quiz.entity.QuizQuestion;
import kr.co.apiy.quiz.entity.QuizSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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

    public List<QuizSetResponse> searchQuizSets(int page) {
        PageRequest pageable = PageRequest.of(page, 10);
        return quizSetRepository
                .findAll(pageable)
                .stream()
                .map(quizSet -> QuizSetResponse
                        .builder()
                        .id(quizSet.getId())
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

        quizSetRepository.save(quizSetEntity);

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
                                .quizQuestion(quizQuestionEntity)
                                .answer(quizAnswerSaveRequest.getAnswer())
                                .isCorrect(quizAnswerSaveRequest.getIsCorrect())
                                .build();

                        quizQuestionEntity.addAnswer(quizAnswerEntity);
                    });

            saveQuestions.add(quizQuestionEntity);
        });

        quizQuestionRepository.saveAll(saveQuestions);
    }

    public List<QuizQuestionResponse> searchQuizQuestions(long quizSetId) {

        List<QuizQuestionResponse> result = new ArrayList<>();

        quizQuestionRepository.findQuizQuestionsByQuizSetId(quizSetId)
                .ifPresentOrElse(
                        (quizQuestionList) -> {
                            log.info(quizQuestionList);
                            QuizSet quizSetEntity = quizQuestionList.get(0).getQuizSet();
                            QuizSetResponse quizSetResponse = QuizSetResponse.builder()
                                    .id(quizSetEntity.getId())
                                    .subject(quizSetEntity.getSubject())
                                    .category(quizSetEntity.getCategory())
                                    .build();

                            quizQuestionList.forEach(
                                    quizQuestion -> {
                                        QuizQuestionResponse quizQuestionResponse = entityToResponse(quizSetResponse, quizQuestion);
                                        result.add(quizQuestionResponse);
                                    }
                            );
                        },
                        () -> {
                            throw new InternalServerException(HttpStatus.BAD_REQUEST, "존재하지 않는 퀴즈입니다");
                        }
                );

        return result;
    }

    public QuizQuestionResponse entityToResponse(QuizSetResponse quizSetResponse, QuizQuestion quizQuestion) {

        List<QuizAnswerResponse> answers = new ArrayList<>();
        List<QuizAnswer> answerEntities = quizQuestion.getAnswers();
        answerEntities.forEach( answerEntity -> {
            QuizAnswerResponse answerResponse = QuizAnswerResponse
                    .builder()
                    .id(answerEntity.getId())
                    .answer(answerEntity.getAnswer())
                    .build();
            answers.add(answerResponse);
        });

        return QuizQuestionResponse.builder()
                .quizSet(quizSetResponse)
                .id(quizQuestion.getId())
                .question(quizQuestion.getQuestion())
                .answers(answers)
                .build();
    }
}
