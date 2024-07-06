package kr.co.apiy.member.dto;

import lombok.Builder;

@Builder
public class SignupResponse {

    private Long id;
    private String email;
    private String name;

}
