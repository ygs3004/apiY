package kr.co.apiy.quiz.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.apiy.quiz.dto.enums.QuizCategory;
import lombok.*;

@Schema(name = "퀴즈 정보")
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuizSetResponse {

    @Schema(description = "퀴즈 정보 ID")
    private long id;

    @Schema(description = "퀴즈 정보 카테고리")
    private QuizCategory category;

    @Schema(description = "퀴즈 정보 주제")
    private String subject;

}
