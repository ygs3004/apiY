package kr.co.apiy.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(name = "로그인 요청")
@Getter
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
