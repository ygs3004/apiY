package kr.co.apiy.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignupDTO {

    @Builder
    @Getter
    public static class Request{
        private String email;
        private String password;
        private String name;
    }

    @Builder
    public static class Response{
        private Long id;
        private String email;
        private String name;
    }

}
