package kr.co.apiy.quiz.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.apiy.quiz.dto.enums.QuizCategory;
import lombok.*;

import java.util.List;

@Schema(name = "퀴즈 정보")
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuizSetResponse {

    private long id;

    private QuizCategory category;

    private String subject;

}
