package kr.co.apiy.today.news;

import kr.co.apiy.today.dto.NewsApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class NewsScheduler {

    private final NewsApi newsApi;
    private final NewsService newsService;

    // 초 분 시간 일 월 요일
    // 매일 자정에 실행: 0 0 0 * * ?
    // 매일 오전 9시 실행: 0 0 9 * * ?
    // 매주 월요일 오전 9시 실행: 0 0 9 ? * MON
    // 매월 1일 자정 실행: 0 0 0 1 * ?
    // 매시간 30분마다 실행: 0 30 * * * ?
    @Scheduled(cron = "0 0 0,6,12,18 * * ?")
    public void updateNewsData() {
        NewsApiResult response = newsApi.getLatestNewsData();
        newsService.saveNewsData(response);
    }

}
