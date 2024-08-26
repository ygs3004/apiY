package kr.co.apiy.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "퀴즈 답안 저장 요청")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class QuizAnswerSaveRequest {

    @NotBlank
    String answer;

    @NotNull
    Boolean isCorrect;

}
