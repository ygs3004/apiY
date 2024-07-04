package kr.co.apiy.global.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> invalid(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<String> invalidData(DataIntegrityViolationException e) {
        e.printStackTrace();
        String message = "DataIntegrityViolationException";
        if(e.getMessage().contains("not-null")){
            message = "필수 입력 데이터가 없습니다.";
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}
