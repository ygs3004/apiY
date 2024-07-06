package kr.co.apiy.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignupRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String name;

}
