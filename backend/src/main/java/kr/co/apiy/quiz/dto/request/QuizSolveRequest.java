package kr.co.apiy.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Schema(name = "퀴즈 풀이 결과 제출")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizSolveRequest {

    @NotNull
    @Schema(description = "퀴즈 ID")
    Long quizSetId;

    List<@Valid QuestionSolve> questionSolves;

    @Schema(name = "문제 풀이")
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionSolve{
        @NotNull
        @Schema(description = "문제에 대해 선택한 문제 ID")
        Long questionId;

        @NotNull
        @Schema(description = "문제에 대해 선택한 정답 ID")
        Long selectedAnswerId;
    }

}
