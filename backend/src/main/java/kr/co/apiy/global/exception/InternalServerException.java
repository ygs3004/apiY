package kr.co.apiy.global.exception;

public class InternalServerException extends RuntimeException{
    public InternalServerException(String msg) {
        super(msg);
    }
}
