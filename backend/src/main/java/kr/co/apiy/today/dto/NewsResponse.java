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

    private String title;

    @JsonProperty("originallink")
    private String originalLink;

    private String link;

    private String description;

    private String pubDate;

}
