package kr.co.apiy.today.movie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class MovieTest {

    @Autowired
    MovieScheduler movieScheduler;

    @Test
    public void movieSchedulerTest() {
        // movieScheduler.updateMovieData();
    }

}