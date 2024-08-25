package kr.co.apiy.quiz;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.apiy.quiz.dto.QuizSetSaveRequest;
import kr.co.apiy.quiz.dto.QuizSetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@Tag(name = "퀴즈 API", description = "퀴즈 제공 API")
@RequestMapping("/quiz/*")
public class QuizController {

    private final QuizService quizService;

    @Operation(summary = "퀴즈 목록 조회", description = "퀴즈 세트 목록을 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = QuizSetResponse.class)))
            }),
    })
    @GetMapping("/set")
    public ResponseEntity<List<QuizSetResponse>> getQuizSets() {
        List<QuizSetResponse> result = quizService.getQuizSets();
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "퀴즈 목록 등록/수정", description = "퀴즈 세트 등록/수정가능합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PutMapping("/set")
    public ResponseEntity<Void> saveQuizSets(
            @Valid
            @Schema(implementation = QuizSetSaveRequest.class)
            QuizSetSaveRequest quizSetSaveRequest
    ) {
        quizService.saveQuizSet();
        return ResponseEntity.ok().build();
    }

}
