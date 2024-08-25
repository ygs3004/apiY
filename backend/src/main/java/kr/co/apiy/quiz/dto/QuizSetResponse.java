package kr.co.apiy.quiz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name = "퀴즈 정보")
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuizSetResponse {

    private QuizCategory category;

    private String setName;

}
