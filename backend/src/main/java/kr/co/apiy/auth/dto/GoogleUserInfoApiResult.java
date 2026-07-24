package kr.co.apiy.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GoogleUserInfoApiResult {

    @NotBlank
    String id;

    @NotBlank
    String email;

    @NotBlank
    String name;

    String given_name;

    String family_name;

    String picture;

    boolean verified_email;

}
