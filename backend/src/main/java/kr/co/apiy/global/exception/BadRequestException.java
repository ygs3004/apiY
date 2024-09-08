package kr.co.apiy.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends RuntimeException{

    private final int code;

    public BadRequestException(String msg) {
        super(msg);
        this.code = HttpStatus.BAD_REQUEST.value();
    }

}
