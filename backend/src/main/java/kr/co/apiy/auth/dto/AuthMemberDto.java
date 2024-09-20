package kr.co.apiy.auth.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthMemberDto extends User {

    private final Long id;

    private final String email;

    private final String name;

    public AuthMemberDto(
            Long id,
            String email,
            String password,
            String name,
            Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
