package kr.co.apiy.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class SignupFailException extends AuthenticationException {
    public SignupFailException(String msg) {
        super(msg);
    }
}
