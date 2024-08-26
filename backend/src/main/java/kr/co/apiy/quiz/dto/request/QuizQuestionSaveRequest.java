package kr.co.apiy.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Schema(name = "퀴즈 질문 저장 요청")
@Getter
@Setter
@AllArgsConstructor
public class QuizQuestionSaveRequest {

    @NotBlank
    private String question;

    private final List<@Valid QuizAnswerSaveRequest> answers = new ArrayList<>();

}
