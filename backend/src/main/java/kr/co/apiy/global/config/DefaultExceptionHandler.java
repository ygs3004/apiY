package kr.co.apiy.global.config;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.apiy.auth.dto.ErrorResponse;
import kr.co.apiy.auth.exception.SignupFailException;
import kr.co.apiy.global.entity.ExceptionLog;
import kr.co.apiy.global.exception.InternalServerException;
import kr.co.apiy.global.log.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Log4j2
public class DefaultExceptionHandler {

    private final LogRepository logRepository;

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> internalServerException(InternalServerException e) {
        log.warn(e.getMessage(), e);
        this.saveErrorLog(e.getCode(), e);
        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse.builder()
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> invalid(RuntimeException e) {
        log.warn(e.getMessage(), e);
        this.saveErrorLog(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse.builder()
                        .code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> invalidData(MethodArgumentNotValidException e) {
        log.warn(e.getMessage(), e);
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .message(message)
                        .build()
                );
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> badRequest(RuntimeException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler({SignupFailException.class})
    public ResponseEntity<ErrorResponse> failSignup(SignupFailException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.builder()
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .message(e.getMessage())
                        .build()
                );
    }

    private void saveErrorLog(int code, Exception e){
        logRepository.save(ExceptionLog
                .builder()
                .errorCode(code)
                .errorMessage(e.getMessage())
                .build());
    }

}
