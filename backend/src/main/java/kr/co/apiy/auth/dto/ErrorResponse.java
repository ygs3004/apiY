package kr.co.apiy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(name="에러응답")
@Getter
@Builder
public class ErrorResponse {

    @Schema(description = "에러 코드")
    private final int code;
    @Schema(description = "에러 메세지")
    private final String message;

}
