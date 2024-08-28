package kr.co.apiy.quiz.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(name = "퀴즈 문제 내용")
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuizQuestionResponse {

    QuizSetResponse quizSet;

    long id;

    String question;

    List<QuizAnswerResponse> answers;

}
