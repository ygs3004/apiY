package kr.co.apiy.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.apiy.quiz.dto.enums.QuizCategory;
import lombok.*;

import java.util.List;

@Schema(name = "퀴즈 저장 요청")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizSetSaveRequest {

    @NotNull
    private QuizCategory category;

    @NotBlank
    private String subject;

    private List<@Valid QuizQuestionSaveRequest> questions;

}
