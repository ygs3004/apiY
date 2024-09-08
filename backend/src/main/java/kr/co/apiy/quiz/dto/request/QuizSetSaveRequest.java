package kr.co.apiy.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.apiy.quiz.dto.enums.QuizCategory;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Schema(name = "퀴즈 저장 요청")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizSetSaveRequest {

    @NotNull
    @Schema(description = "퀴즈 테고리")
    private QuizCategory category;

    @NotBlank
    @Schema(description = "퀴즈 주제")
    private String subject;

    @Size(min = 1, message = "등록 신청한 문제가 없습니다.")
    @Schema(description = "문제 목록")
    private List<@Valid Question> questions = new ArrayList<>();

    @Schema(name = "퀴즈 문제 저장 요청")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Question {

        @NotBlank
        @Schema(description = "문제 내용")
        private String question;

        @Size(min = 2, message = "최소 두개의 보기가 필요합니다.")
        @Schema(description = "문제 보기")
        private List<@Valid Answer> answers = new ArrayList<>();

    }

    @Schema(name = "퀴즈 답안 저장 요청")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Answer {

        @NotBlank
        @Schema(description = "답안 내용")
        String answer;

        @NotNull
        @Schema(description = "정답, 오답정보")
        Boolean isCorrect;

    }


}
