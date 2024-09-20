package kr.co.apiy.global.sample;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@Tag(name = "샘플", description = "Javadoc,Swagger TEST")
@RequestMapping("/sample")
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
            @ApiResponse(responseCode = "200", description = "성공", content = {@Content(schema = @Schema(implementation = SampleDto.Response.class))}),
            // @ApiResponse(responseCode = "404", description = "해당 ID의 유저가 존재하지 않습니다."),
    })
    @PostMapping("/test")
    public ResponseEntity<SampleDto.Response> testSample(
            @Valid
            @RequestBody
            @Schema(implementation = SampleDto.Request.class)
            SampleDto.Request sampleRequest,

            @RequestParam("queryParam1")
            @Parameter(description = "Member ID", example = "1")
            int queryParam1,

            @RequestParam("queryParam2")
            @Parameter(name = "queryParam2", description = "로그인 유저 ID 값", example = "3", required = true)
            int queryParam2
    ) {
        SampleDto.Response sampleResponse = new SampleDto.Response();
        String resultMessage = returnResult(sampleRequest, queryParam1, queryParam2);
        sampleResponse.setMessage(resultMessage);

        log.info("Sample Response >>>>> {}", sampleResponse);
        return ResponseEntity.ok(sampleResponse);
    }

    private String returnResult(SampleDto.Request sampleRequest, int queryParam1, int queryParam2) {
        return "result: = " + sampleRequest.getParameter1() + ", " + sampleRequest.getParameter2() + System.lineSeparator() + "query1: " + queryParam1 +", query2: " + queryParam2;
    }

    @PostMapping("/security")
    @Operation(summary = "Security Sample API", description = "로그인이 처리 테스트입니다.", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<String> testSample() {
        return ResponseEntity.ok().body("성공");
    }

    @PostMapping("/healthcheck")
    @Operation(summary = "Healthcheck", description = "서버 연결 확인테스트입니다.")
    public ResponseEntity<String> healthcheck() {
        String success = "Healthcheck: Success";
        log.info(success);
        return ResponseEntity.ok(success);
    }

}
