package kr.co.apiy.today.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News {

    private String title;

    @JsonProperty("originallink")
    private String originalLink;

    private String link;

    private String description;

    private String pubDate;

}
