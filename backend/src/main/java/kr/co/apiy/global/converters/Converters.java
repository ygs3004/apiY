package kr.co.apiy.global.converters;

import jakarta.persistence.Converter;
import kr.co.apiy.auth.dto.MemberRole;
import kr.co.apiy.quiz.dto.enums.QuizCategory;
import kr.co.apiy.today.dto.RankInto;

public class Converters {

    @Converter
    public static class ForMemberRole extends DefaultEnumConverter<MemberRole>{
        ForMemberRole() {
            super(MemberRole.class);
        }
    }

    @Converter
    public static class ForRankInto extends DefaultEnumConverter<RankInto>{
        ForRankInto() {
            super(RankInto.class);
        }
    }

    @Converter
    public static class ForQuizCategory extends DefaultEnumConverter<QuizCategory>{
        ForQuizCategory() {
            super(QuizCategory.class);
        }

        @Override
        public QuizCategory convertToEntityAttribute(String dbData) {
            return QuizCategory.fromValue(dbData);
        }
    }

}
