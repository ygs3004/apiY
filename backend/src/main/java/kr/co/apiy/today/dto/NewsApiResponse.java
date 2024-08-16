package kr.co.apiy.today.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class NewsApiResponse {

    private String lastBuildDate;

    private int total;

    private int start;

    private int display;

    private List<NewsItem> items;

}
