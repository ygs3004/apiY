package kr.co.apiy.global.utils;

import jakarta.persistence.Converter;
import kr.co.apiy.auth.entity.MemberRole;

public class Converters {

    @Converter
    public static class ForMemberRole extends DefaultEnumConverter<MemberRole>{
        ForMemberRole() {
            super(MemberRole.class);
        }
    }

}
