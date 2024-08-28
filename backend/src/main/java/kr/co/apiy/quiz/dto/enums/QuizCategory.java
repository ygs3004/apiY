package kr.co.apiy.quiz.dto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Schema(name = "퀴즈 카테고리", example = "ENGLISH, MATH, GAME, ETC")
public enum QuizCategory {
    ENGLISH("영어"), MATH("수학"), GAME("게임"), ETC("기타");

    String korName;

    QuizCategory(String korName) {
        this.korName = korName;
    }

    @JsonValue
    public String getValue() {
        return this.korName;
    }

    @JsonCreator
    public static QuizCategory fromValue(String value) {
        log.info("value: {}", value);
        for (QuizCategory quizCategory : QuizCategory.values()) {
            if (quizCategory.name().equalsIgnoreCase(value)) {
                return quizCategory;
            }
        }
        throw new IllegalArgumentException("잘못된 퀴즈 카테고리입니다.: " + value);
    }
}
