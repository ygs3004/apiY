package kr.co.apiy.today.dto;

import lombok.*;

import java.util.List;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class NewsApiResult {

    private String lastBuildDate;

    private int total;

    private int start;

    private int display;

    private List<News> items;

}
