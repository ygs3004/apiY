package kr.co.apiy.today.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsItem {

    private String title;

    @JsonProperty("originallink")
    private String originalLink;

    private String link;

    private String description;

    private String pubDate;

}
