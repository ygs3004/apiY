package kr.co.apiy.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class SocialLoginFailException extends AuthenticationException {
    public SocialLoginFailException(String msg) {
        super(msg);
    }
}
