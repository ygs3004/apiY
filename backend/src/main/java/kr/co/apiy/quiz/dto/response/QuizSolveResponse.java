package kr.co.apiy.quiz.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name = "퀴즈 풀이 결과")
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuizSolveResponse {

    @Schema(description = "문제 수")
    Integer questionCount;

    @Schema(description = "정답 수")
    Integer correctCount;
    
}
