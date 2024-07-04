package kr.co.apiy.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
public class LoginDTO {

    @Getter
    public static class Request{
        private String email;
        private String password;
    }

    @Builder
    public static class Response{
        private String email;
        private String name;
    }

}
