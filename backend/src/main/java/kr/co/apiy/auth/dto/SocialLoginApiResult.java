package kr.co.apiy.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SocialLoginApiResult {

    @NotBlank
    String access_token;

    @NotBlank
    String expires_in;

    @NotBlank
    String id_token;

    String scope;

    String token_type;

}
