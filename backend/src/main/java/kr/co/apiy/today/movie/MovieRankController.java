package kr.co.apiy.today.movie;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.apiy.today.dto.MovieRankResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@Tag(name = "영화 API", description = "영화 박스오피스 순위 정보")
@RequestMapping("/movie/*")
public class MovieRankController {

    private final MovieRankService movieRankService;

    @Operation(summary = "일간 박스오피스 순위 정보", description = "요청일 기준 이전 일자의 일간 박스오피스 순위정보를 제공합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = MovieRankResponse.class)))
            }),
    })
    @GetMapping("/rank/yesterday")
    public ResponseEntity<List<MovieRankResponse>> getNews() {
        return ResponseEntity.ok(movieRankService.getYesterdayMovieRank());
    }

}
