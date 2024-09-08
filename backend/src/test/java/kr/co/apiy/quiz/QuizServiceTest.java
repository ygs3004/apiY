package kr.co.apiy.quiz;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
@ActiveProfiles("test")
public class QuizServiceTest {

    @Autowired
    private QuizService quizService;

    @Transactional
    @Test
    public void quizSearchTest() {
        log.info(quizService.searchQuizSets(0));
        log.info(quizService.searchQuizQuestions(1));
    }

}
