package kr.co.apiy.auth.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtParseResult {

    private String email;
    private Date expiration;

    public boolean isValid(){
        Date now = new Date();
        boolean isNowExpired = now.before(this.expiration);
        boolean isValidEmail = this.email.length() > 0;
        return isNowExpired && isValidEmail;
    }

}
