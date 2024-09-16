package kr.co.apiy.today.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name = "최신 뉴스 정보")
@ToString
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse {

    @Schema(description = "제목")
    private String title;

    @JsonProperty("originallink")
    @Schema(description = "원본링크")
    private String originalLink;

    @Schema(description = "링크")
    private String link;

    @Schema(description = "자세히")
    private String description;

    @Schema(description = "발행일")
    private String pubDate;

}
