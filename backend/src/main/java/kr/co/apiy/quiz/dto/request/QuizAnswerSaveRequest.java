package kr.co.apiy.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(name = "퀴즈 답안 저장 요청")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizAnswerSaveRequest {

    @NotBlank
    String answer;

    @NotNull
    Boolean isCorrect;

}
