package kr.co.apiy.dto;

import kr.co.apiy.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class LoginDTO {

    private String email;
    private String password;
    private String name;

}
