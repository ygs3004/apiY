package kr.co.apiy.today.news;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.apiy.today.dto.NewsResponse;
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
@Tag(name = "뉴스 API", description = "최신 뉴스정보 확인")
@RequestMapping("/news/*")
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "뉴스 정보", description = "최신 뉴스 정보를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = NewsResponse.class)))
            }),
    })
    @GetMapping("/latest")
    public ResponseEntity<List<NewsResponse>> getLatestNews() {
        return ResponseEntity.ok(newsService.getLatestNews());
    }

}
