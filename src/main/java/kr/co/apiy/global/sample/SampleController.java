package kr.co.apiy.global.sample;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "샘플", description = "Javadoc,Swagger TEST")
public class SampleController {

    // Array Response
    // @ApiResponses(value = {
    // 	@ApiResponse(responseCode = "200", description = "성공",
    // 		content = {
    // 			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MemberRes.class)))
    // 		})
    // })

    @Operation(summary = "Sample API", description = "테스트합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {@Content(schema = @Schema(implementation = SampleDto.Request.class))}),
            @ApiResponse(responseCode = "404", description = "해당 ID의 유저가 존재하지 않습니다."),
    })
    @PostMapping("/sample")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SampleDto.Response> testSample(
            @Valid
            @RequestBody
            @Schema(implementation = SampleDto.Request.class)
            SampleDto.Request sampleRequest,

            @Positive(message = "유저 ID는 양수입니다.")
            @Schema(description = "Member ID", example = "1")
            int queryParam1,

            @Positive(message="유저 ID는 양수입니다.")
            @Parameter(name = "query2", description = "로그인 유저 ID 값", example = "3", required = true)
            int queryParam2
    ) {
        SampleDto.Response sampleResponse = new SampleDto.Response();
        String resultMessage = returnResult(sampleRequest, queryParam1, queryParam2);
        sampleResponse.setMessage(resultMessage);
        return ResponseEntity.ok(sampleResponse);
    }

    private String returnResult(SampleDto.Request sampleRequest, int queryParam1, int queryParam2) {
        return "result: = " + sampleRequest.getParameter1() + ", " + sampleRequest.getParameter2() + System.lineSeparator() + "query1: " + queryParam1 +", query2: " + queryParam2;
    }
}
