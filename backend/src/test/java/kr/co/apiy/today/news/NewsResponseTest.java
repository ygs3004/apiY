package kr.co.apiy.today.news;

import kr.co.apiy.today.dto.NewsApiResult;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Log4j2
@ActiveProfiles("test")
public class NewsResponseTest {

    @Autowired
    NewsApi newsApi;

    @Autowired
    NewsService newsService;

    @Test
    @Commit
    public void getLatestNewsTest() {
        NewsApiResult response = newsApi.getLatestNewsData();
        newsService.saveNewsData(response);
        log.info(newsService.getLatestNews());
    }

}
