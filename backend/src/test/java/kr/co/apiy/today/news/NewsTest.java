package kr.co.apiy.today.news;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class NewsTest {

    @Autowired
    NewsScheduler newsScheduler;

    @Autowired
    NewsService newsService;

    @Test
    @Commit
    public void updateNewsTest() {
        newsScheduler.updateNewsData();
    }

    @Test
    public void getNewsTest() {
        newsService.getLatestNews().forEach(System.out::println);
    }

}
