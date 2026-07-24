package kr.co.apiy.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString(exclude = "client_secret")
public class SocialLoginApiRequest {
    @NotBlank
    String code;

    @NotBlank
    String client_id;

    @NotBlank
    String client_secret;

    @NotBlank
    String redirect_uri;

    @NotBlank
    String grant_type;

}
