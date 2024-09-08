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

    @Schema(description = "퀴즈 정보")
    QuizSetResponse quizSet;

    @Schema(description = "퀴즈 문제 ID")
    long id;

    @Schema(description = "퀴즈 문제 내용")
    String question;

    @Schema(description = "퀴즈 답안 보기")
    List<Answers> answers;

    @Schema(name = "퀴즈 답안 보기")
    @Builder
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Answers {

        @Schema(description = "퀴즈 답안 ID")
        long id;

        @Schema(description = "퀴즈 답안 내용")
        String answer;

    }


}
