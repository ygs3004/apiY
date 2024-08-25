package kr.co.apiy.global.sample;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@AllArgsConstructor
public class SampleDto {

    // @NotNull: 값이 null이 아니어야 함.
    // @Null: 값이 반드시 null이어야 함.
    // @NotEmpty: 문자열, 컬렉션, 맵, 배열 등이 null이 아니고, 비어 있지 않아야 함.
    // @NotBlank: 문자열이 null이 아니고, 공백이 아니어야 함 (길이가 0이 아닌 상태).
    // @AssertTrue: 값이 true여야 함.
    // @AssertFalse: 값이 false여야 함.
    // @Min(value): 숫자가 지정된 최소값 이상이어야 함.
    // @Max(value): 숫자가 지정된 최대값 이하이어야 함.
    // @Size(min, max): 문자열, 컬렉션, 맵, 배열 등의 크기나 길이가 지정된 범위 내에 있어야 함.
    // @Email: 문자열이 유효한 이메일 주소 형식이어야 함.
    // @Pattern(regexp): 문자열이 지정된 정규식과 일치해야 함.
    // @Past: 날짜가 과거여야 함.
    // @PastOrPresent: 날짜가 과거 또는 현재여야 함.
    // @Future: 날짜가 미래여야 함.
    // @FutureOrPresent: 날짜가 미래 또는 현재여야 함.
    // @Positive: 숫자가 양수여야 함.
    // @PositiveOrZero: 숫자가 0 또는 양수여야 함.
    // @Negative: 숫자가 음수여야 함.
    // @NegativeOrZero: 숫자가 0 또는 음수여야 함.
    // @Digits(integer, fraction): 숫자가 지정된 자릿수 내에 있어야 함 (정수 자리수와 소수 자리수).

    @Schema(name = "SampleRequest", description = "Sample Request 형식입니다.")
    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        @Schema(description = "param1 입니다.", example = "Aaa")
        @NotBlank(message = "필수 입력값입니다.")
        private String parameter1;

        @NotBlank(message = "필수 입력값입니다.")
        private String parameter2;
    }

    @Schema(name = "SampleResponse", description = "Response 객체")
    @Setter
    @Getter
    public static class Response{
        private String message;
    }

}
