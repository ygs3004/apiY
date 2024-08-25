package kr.co.apiy.quiz;

import kr.co.apiy.quiz.dto.QuizSetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class QuizService {

    private final QuizQuestionRepository quizQuestionRepository;

    public List<QuizSetResponse> getQuizSets() {
        return null;
    }

    public void saveQuizSet() {
    }

}
