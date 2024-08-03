package kr.co.apiy.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "회원가입 요청")
@Builder
@Getter
public class SignupRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String name;

}
