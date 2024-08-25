package kr.co.apiy.quiz.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "QUIZ_ANSWER")
@Builder
@Getter
@ToString(exclude = "quiz")
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUIZ_QUESTION_ID")
    QuizQuestion quizQuestion;

}
