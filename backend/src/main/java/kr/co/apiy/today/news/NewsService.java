package kr.co.apiy.today.news;

import kr.co.apiy.global.Constants;
import kr.co.apiy.today.dto.NewsApiResult;
import kr.co.apiy.today.dto.News;
import kr.co.apiy.today.entity.NewsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

@Service
@Log4j2
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsApi newsApi;

    public List<News> getLatestNews() {

        this.checkLatest();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("pubDate").descending());
        Page<NewsEntity> result = newsRepository.findAll(pageable);
        return result.getContent()
                .stream()
                .map(newsEntity -> News.builder()
                        .title(
                                Jsoup.parse(newsEntity.getTitle()).text()
                        )
                        .originalLink(newsEntity.getOriginalLink())
                        .link(newsEntity.getLink())
                        .description(
                                Jsoup.parse(newsEntity.getDescription()).text()
                        )
                        .pubDate(this.LocalDateToString(newsEntity.getPubDate()))
                        .build())
                .toList();
    }

    public void checkLatest() {
        Pageable maxPageable = PageRequest.of(0, 1, Sort.by("pubDate").descending());
        Page<NewsEntity> maxResult = newsRepository.findAll(maxPageable);
        AtomicBoolean needUpdate = new AtomicBoolean(false);
        maxResult.getContent().stream().findFirst().ifPresentOrElse(
                newsEntity -> {
                    LocalDateTime lastModifyDate = newsEntity.getModDate();
                    LocalDateTime now = LocalDateTime.now();
                    long hourDif = Duration.between(lastModifyDate, now).toHours();
                    log.info("최신 lastModifyDate 와 현재 시간차이: {}", hourDif);
                    if(hourDif > 6) needUpdate.set(true);
                },
                () -> {
                    needUpdate.set(true);
                }
        );

        if(needUpdate.get()){
            NewsApiResult response = newsApi.getLatestNewsData();
            saveNewsData(response);
        }
    }

    public void saveNewsData(NewsApiResult newsApiResult) {
        newsApiResult.getItems().forEach(news -> {
            String newsTitle = news.getTitle();
            int titleLength = newsTitle.length();
            String titleHead = newsTitle.substring(0, Math.min(titleLength, 20));

            String description = news.getDescription();
            int descriptionLength = description.length();
            String descriptionHead = newsTitle.substring(0, Math.min(descriptionLength, 20));

            boolean isKoreanNews = this.isKoreanString(titleHead);
            isKoreanNews = isKoreanNews && this.isKoreanString(descriptionHead);

            Optional<NewsEntity> newsEntity = newsRepository.findByTitleLike(titleHead + "%");
            boolean hasNoData = newsEntity.isEmpty();

            if(hasNoData && isKoreanNews){
                newsRepository.save(NewsEntity.builder()
                        .title(news.getTitle())
                        .link(news.getLink())
                        .description(news.getDescription())
                        .pubDate(parseDate(news.getPubDate()))
                        .originalLink(news.getOriginalLink())
                        .build());
            }
        });
    }

    public boolean isKoreanString(String str){
        return Arrays.stream(str.split("")).anyMatch(
                descriptionWord -> Pattern.compile("[ㄱ-ㅎㅏ-ㅣ가-힣]").matcher(descriptionWord).find()
        );
    }

    public LocalDateTime parseDate(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(date, inputFormatter);
        ZoneId desiredZoneId = ZoneId.of(Constants.TIME_ZONE_OF_SEOUL);
        ZonedDateTime convertedZonedDateTime = zonedDateTime.withZoneSameInstant(desiredZoneId);
        return convertedZonedDateTime.toLocalDateTime();
    }

    public String LocalDateToString(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDateTime.format(formatter);
    }
}
