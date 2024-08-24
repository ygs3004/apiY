package kr.co.apiy.global.config;

import kr.co.apiy.global.entity.ExceptionLogEntity;
import kr.co.apiy.global.exception.InternalServerException;
import kr.co.apiy.global.log.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Log4j2
public class DefaultExceptionHandler {

    private final LogRepository logRepository;

    @ExceptionHandler(value = InternalServerException.class)
    public ResponseEntity<String> internalServerException(InternalServerException e) {
        log.warn(e.getMessage(), e);
        this.saveErrorLog(e.getCode(), e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> invalid(RuntimeException e) {
        log.warn(e.getMessage(), e);
        this.saveErrorLog(HttpStatus.INTERNAL_SERVER_ERROR.value(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> invalidData(MethodArgumentNotValidException e) {
        log.warn(e.getMessage(), e);
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    private void saveErrorLog(int code, Exception e){
        logRepository.save(ExceptionLogEntity
                .builder()
                .errorCode(code)
                .errorMessage(e.getMessage())
                .build());
    }

}
