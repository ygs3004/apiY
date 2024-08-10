package kr.co.apiy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Schema(name = "회원가입 응답")
@Builder
public class SignupResponse {

    @NotBlank
    private Long id;

    @NotBlank
    private String email;

    private String name;

}
