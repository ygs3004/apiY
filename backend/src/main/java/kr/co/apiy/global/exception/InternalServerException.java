package kr.co.apiy.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InternalServerException extends RuntimeException{

    private int code;

    public InternalServerException(String msg) {
        super(msg);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }


    public InternalServerException(HttpStatus httpStatus, String msg) {
        super(msg);
        this.code = httpStatus.value();
    }

}
