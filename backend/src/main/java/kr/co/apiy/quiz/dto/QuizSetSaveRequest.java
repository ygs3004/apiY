package kr.co.apiy.quiz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "로그인 요청")
@Getter
@Setter
@Builder
public class QuizSetSaveRequest {

    @Email
    @NotBlank
    private QuizCategory category;

    @NotBlank
    private String password;

}
