package kr.co.apiy.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(name = "로그인 응답")
@Builder
public class LoginResponse {

    private String email;
    private String name;

}
