package kr.co.apiy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SignupDTO {
    private String email;
    private String password;
    private String name;
}
