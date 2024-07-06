package kr.co.apiy.member.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginFailException extends AuthenticationException {
    public LoginFailException(String msg) {
        super(msg);
    }
}
