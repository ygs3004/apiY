package kr.co.apiy.quiz.entity;

import jakarta.persistence.*;
import kr.co.apiy.global.entity.BaseEntity;
import lombok.*;

@Entity
@Table(name = "QUIZ_ANSWER")
@Builder
@Getter
@ToString(exclude = "quiz")
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUIZ_QUESTION_ID")
    QuizQuestion quizQuestion;

}
