package kr.co.apiy.today.news;

import kr.co.apiy.global.utils.Constants;
import kr.co.apiy.global.utils.StringUtils;
import kr.co.apiy.today.dto.NewsResponse;
import kr.co.apiy.today.dto.NewsApiResult;
import kr.co.apiy.today.entity.News;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public List<NewsResponse> getLatestNews() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pubDate").descending());
        Page<News> result = newsRepository.findAll(pageable);
        return result.getContent()
                .stream()
                .map(news -> NewsResponse.builder()
                        .title(
                                Jsoup.parse(news.getTitle()).text()
                        )
                        .originalLink(news.getOriginalLink())
                        .link(news.getLink())
                        .description(
                                Jsoup.parse(news.getDescription()).text()
                        )
                        .pubDate(StringUtils.LocalDateTimeToGlobalFormat(news.getPubDate()))
                        .build())
                .toList();
    }

    public void saveNewsData(NewsApiResult newsApiResult) {
        newsApiResult.getItems().forEach(newsResponse -> {
            String newsTitle = newsResponse.getTitle();
            int titleLength = newsTitle.length();
            String titleHead = newsTitle.substring(0, Math.min(titleLength, 20));

            String description = newsResponse.getDescription();
            int descriptionLength = description.length();
            String descriptionHead = description.substring(0, Math.min(descriptionLength, 20));

            boolean isKoreanNews = this.isKoreanString(titleHead);
            isKoreanNews = isKoreanNews && this.isKoreanString(descriptionHead);

            Optional<News> newsEntity = newsRepository.findByTitleLike(titleHead + "%");
            boolean hasNoData = newsEntity.isEmpty();

            if(hasNoData && isKoreanNews){
                newsRepository.save(News.builder()
                        .title(newsResponse.getTitle())
                        .link(newsResponse.getLink())
                        .description(newsResponse.getDescription())
                        .pubDate(this.parseDate(newsResponse.getPubDate()))
                        .originalLink(newsResponse.getOriginalLink())
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

}
