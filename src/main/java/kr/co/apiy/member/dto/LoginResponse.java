package kr.co.apiy.member.dto;

import lombok.Builder;

@Builder
public class LoginResponse {

    private String email;
    private String name;

}
