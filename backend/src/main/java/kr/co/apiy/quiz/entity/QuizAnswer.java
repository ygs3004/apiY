package kr.co.apiy.quiz.entity;

import jakarta.persistence.*;
import kr.co.apiy.global.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "QUIZ_ANSWER")
@Builder
@Getter
@ToString(exclude = "quizQuestion")
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUIZ_QUESTION_ID")
    QuizQuestion quizQuestion;

    @Comment("내용")
    @Column(nullable = false)
    String answer;

    @Comment("정답여부")
    @Column(nullable = false)
    Boolean isCorrect;


}
