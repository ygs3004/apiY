package kr.co.apiy.quiz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name = "퀴즈 문제 내용")
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuizQuestionResponse {
}
