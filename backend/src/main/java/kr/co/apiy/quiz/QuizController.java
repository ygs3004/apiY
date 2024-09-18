package kr.co.apiy.quiz;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.apiy.auth.dto.AuthMemberDto;
import kr.co.apiy.auth.dto.ComboResponse;
import kr.co.apiy.quiz.dto.request.QuizSetSaveRequest;
import kr.co.apiy.quiz.dto.request.QuizSolveRequest;
import kr.co.apiy.quiz.dto.response.QuizQuestionResponse;
import kr.co.apiy.quiz.dto.response.QuizSetResponse;
import kr.co.apiy.quiz.dto.response.QuizSolveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@Tag(name = "퀴즈 API", description = "퀴즈 제공 API")
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    @Operation(summary = "퀴즈 카테고리 종류", description = "사용 가능한 카테고리 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ComboResponse.class)))
            }),
    })
    @GetMapping("/combo/category")
    public ResponseEntity<ComboResponse> searchQuizCategory() {
        ComboResponse result = quizService.searchQuizCategory();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "퀴즈 목록 조회", description = "퀴즈 세트 목록을 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = QuizSetResponse.class)))
            }),
    })
    @GetMapping("/set")
    public ResponseEntity<List<QuizSetResponse>> searchQuizSets(
            @Valid @RequestParam int page
    ) {
        List<QuizSetResponse> result = quizService.searchQuizSets(page);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "퀴즈 목록 등록", description = "퀴즈 세트 등록/수정가능합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PutMapping("/set")
    public ResponseEntity<Void> saveQuizSets(
            @Valid @RequestBody
            @Schema(implementation = QuizSetSaveRequest.class)
            QuizSetSaveRequest quizSetSaveRequest
    ) {
        quizService.saveQuizSet(quizSetSaveRequest);
        return ResponseEntity. ok().build();
    }

    @Operation(summary = "퀴즈 조회", description = "퀴즈를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = QuizQuestionResponse.class)))
            }),
    })
    @GetMapping("/question")
    public ResponseEntity<List<QuizQuestionResponse>> searchQuizQuestions(
            @Valid @RequestParam long quizSetId
    ) {
        List<QuizQuestionResponse> result = quizService.searchQuizQuestions(quizSetId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "퀴즈 완료", description = "퀴즈 풀이 후 제출하여 결과를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = QuizSolveResponse.class)))
            }),
            @ApiResponse(responseCode = "400", description = "해당 퀴즈 정보가 존재하지 않거나 잘못된 요청")
    })
    @PostMapping("/solve")
    public ResponseEntity<QuizSolveResponse> searchQuizQuestions(
            @Valid @RequestBody QuizSolveRequest quizSolveRequest,
            @AuthenticationPrincipal AuthMemberDto authMemberDto
            ) {
        QuizSolveResponse result = quizService.checkQuizSolveResult(quizSolveRequest, authMemberDto);
        return ResponseEntity.ok(result);
    }

}
