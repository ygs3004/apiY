package kr.co.apiy.quiz.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum QuizCategory {
    MATH, GAME, ENGLISH, ETC;

    @JsonValue
    public String getValue() {
        return this.name();
    }

    @JsonCreator
    public static QuizCategory fromValue(String value) {
        for (QuizCategory quizCategory : QuizCategory.values()) {
            if (quizCategory.name().equalsIgnoreCase(value)) {
                return quizCategory;
            }
        }
        throw new IllegalArgumentException("잘못된 퀴즈 카테고리입니다.: " + value);
    }
}
