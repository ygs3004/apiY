package kr.co.apiy.global.sample;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
public class SampleDto {

    @Schema(name = "SampleRequest", description = "Sample Request 형식입니다.")
    @Setter
    @Getter
    public static class Request{
        @Schema(description = "param1 입니다.", example = "Aaa")
        @NotBlank(message = "필수 입력값입니다.")
        private String parameter1;

        @NotBlank(message = "필수 입력값입니다.")
        private String parameter2;
    }

    @Schema(name = "SampleResponse", description = "Response 객체")
    @Setter
    public static class Response{
        private String message;
    }

}
