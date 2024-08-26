package kr.co.apiy.quiz.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Schema(name = "퀴즈 답안 응답")
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuizAnswerResponse {

    @NotBlank
    String answer;

    @NotNull
    Boolean isCorrect;

}
