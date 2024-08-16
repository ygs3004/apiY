package kr.co.apiy.today.news;

import kr.co.apiy.today.dto.NewsApiResponse;
import kr.co.apiy.today.dto.NewsItem;
import kr.co.apiy.today.entity.NewsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public void saveScheduleData(NewsApiResponse newsApiResponse) {
        List<NewsEntity> newsEntities = new ArrayList<>();
        newsApiResponse.getItems().forEach(newsItem -> {
            String newsTitle = newsItem.getTitle();
            int titleLength = newsTitle.length();
            Optional<NewsEntity> newsEntity = newsRepository.findByTitleLike(newsTitle.substring(0, Math.min(titleLength, 20)) + "%");
            if(newsEntity.isEmpty()){
                newsRepository.save(NewsEntity.builder()
                        .title(newsItem.getTitle())
                        .link(newsItem.getLink())
                        .description(newsItem.getDescription())
                        .pubDate(parseDate(newsItem.getPubDate()))
                        .originalLink(newsItem.getOriginalLink())
                        .build());
            }
        });

    }

    public LocalDateTime parseDate(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(date, inputFormatter);
        ZoneId desiredZoneId = ZoneId.of("Asia/Seoul");
        ZonedDateTime convertedZonedDateTime = zonedDateTime.withZoneSameInstant(desiredZoneId);
        return convertedZonedDateTime.toLocalDateTime();
    }

    public List<NewsItem> getLatestNews() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pubDate").descending());
        Page<NewsEntity> result = newsRepository.findAll(pageable);
        return  result.getContent().
                stream()
                .map(newsEntity -> NewsItem.builder()
                        .title(
                                newsEntity.getTitle()
                                        .replace("<b>", "")
                                        .replace("</b>", "")
                        )
                        .originalLink(newsEntity.getOriginalLink())
                        .link(newsEntity.getLink())
                        .description(
                                newsEntity.getDescription()
                                        .replace("<b>", "")
                                        .replace("</b>", "")
                        )
                        .pubDate(LocalDateToString(newsEntity.getPubDate()))
                        .build())
                .toList();
    }

    public String LocalDateToString(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDateTime.format(formatter);
    }
}
