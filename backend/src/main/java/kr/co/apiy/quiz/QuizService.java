package kr.co.apiy.quiz;

import jakarta.validation.Valid;
import kr.co.apiy.auth.dto.AuthMemberDto;
import kr.co.apiy.auth.dto.ComboResponse;
import kr.co.apiy.auth.entity.Member;
import kr.co.apiy.auth.member.MemberRepository;
import kr.co.apiy.global.exception.BadRequestException;
import kr.co.apiy.global.exception.InternalServerException;
import kr.co.apiy.quiz.dto.enums.QuizCategory;
import kr.co.apiy.quiz.dto.request.QuizSetSaveRequest;
import kr.co.apiy.quiz.dto.request.QuizSolveRequest;
import kr.co.apiy.quiz.dto.response.QuizQuestionResponse;
import kr.co.apiy.quiz.dto.response.QuizSetResponse;
import kr.co.apiy.quiz.dto.response.QuizSolveResponse;
import kr.co.apiy.quiz.entity.QuizAnswer;
import kr.co.apiy.quiz.entity.QuizQuestion;
import kr.co.apiy.quiz.entity.QuizSet;
import kr.co.apiy.quiz.entity.QuizSolveHistory;
import kr.co.apiy.quiz.repository.QuizQuestionRepository;
import kr.co.apiy.quiz.repository.QuizSetRepository;
import kr.co.apiy.quiz.repository.QuizSolveHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class QuizService {

    private final QuizSetRepository quizSetRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizSolveHistoryRepository quizSolveHistoryRepository;
    private final MemberRepository memberRepository;

    public ComboResponse searchQuizCategory() {

        ComboResponse comboResponse = ComboResponse.builder().name("퀴즈 카테고리").build();
        List<ComboResponse.ComboItem> comboItems = comboResponse.getComboList();

        for(QuizCategory category : QuizCategory.values()){
            ComboResponse.ComboItem comboItem = ComboResponse.ComboItem.builder()
                    .label(category.getValue())
                    .value(category.name())
                    .build();
            comboItems.add(comboItem);
        }

        return comboResponse;
    }

    public List<QuizSetResponse> searchQuizSets(int page) {
        PageRequest pageable = PageRequest.of(page, 10, Sort.by("regDate").descending());
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
                .subject(quizSetSaveRequest.getSubject().trim())
                .category(quizSetSaveRequest.getCategory())
                .build();

        log.info("TEST >>>>>> {}", quizSetEntity.getSubject());
        log.info("TEST >>>>>> {}", quizSetEntity.getCategory());

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

        quizQuestionRepository.findQuizQuestionsByQuizSetIdOrderById(quizSetId)
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

        List<QuizQuestionResponse.Answers> answers = new ArrayList<>();
        List<QuizAnswer> answerEntities = quizQuestion.getAnswers();
        answerEntities.forEach( answerEntity -> {
            QuizQuestionResponse.Answers answerResponse = QuizQuestionResponse.Answers
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

    public QuizSolveResponse checkQuizSolveResult(QuizSolveRequest quizSolveRequest, AuthMemberDto authMemberDto) {

        Long quizSetId = quizSolveRequest.getQuizSetId();
        List<QuizSolveRequest.QuestionSolve> questionSolves = quizSolveRequest.getQuestionSolves();
        questionSolves.sort((solve1, solve2)-> Math.toIntExact(solve1.getQuestionId() - solve2.getQuestionId()));

        Optional<List<QuizQuestion>> optionalQuizQuestions =  quizQuestionRepository.findQuizQuestionsByQuizSetIdOrderById(quizSetId);

        if(optionalQuizQuestions.isPresent()){
            List<QuizQuestion> questions = optionalQuizQuestions.get();
            // 퀴즈 100개중 20개 제공 형태로 수량을 제한하는 로직 추가를 위해
            // id로 문제 비교하는 투포인트 로직 추가
            int solveIdx = 0;
            int questionCount = questionSolves.size();
            int correctCount = 0;
            for (QuizQuestion question : questions) {
                QuizSolveRequest.QuestionSolve solve = questionSolves.get(solveIdx);
                if (question.getId().equals(solve.getQuestionId())) {
                    solveIdx++;
                    boolean isCorrect = question.getAnswers().stream().allMatch(answer -> {
                        boolean isSelectedAnswer = answer.getId().equals(solve.getSelectedAnswerId());
                        boolean isCorrected = answer.getIsCorrect();
                        return (isSelectedAnswer && isCorrected) || (!isSelectedAnswer && !isCorrected);
                    });
                    if (isCorrect) correctCount++;
                }
            }

            QuizSet quizSet = optionalQuizQuestions.get().get(0).getQuizSet();
            saveSolveHistory(quizSet, correctCount, questionCount, authMemberDto);
            return QuizSolveResponse.builder()
                    .questionCount(questionCount)
                    .correctCount(correctCount)
                    .build();
        }else{
            throw new BadRequestException("퀴즈 정보가 존재하지 않습니다.");
        }
    }

    private void saveSolveHistory(QuizSet quizSet, int correctCount, int questionCount, AuthMemberDto authMemberDto){
        Member loginMember = null;
        if (authMemberDto != null) {
            Optional<Member> findMember = memberRepository.findById(authMemberDto.getId());
            if (findMember.isPresent()) {
                loginMember = findMember.get();
            }
        }

        QuizSolveHistory quizSolveHistory = QuizSolveHistory.builder()
                .quizSet(quizSet)
                .member(loginMember)
                .correctCount(correctCount)
                .questionCount(questionCount)
                .build();

        quizSolveHistoryRepository.save(quizSolveHistory);
    }

}
