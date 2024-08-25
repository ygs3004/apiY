package kr.co.apiy.quiz.entity;

import jakarta.persistence.*;
import kr.co.apiy.global.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "QUIZ_QUESTION")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"quizSet", "answers"})
public class QuizQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Comment("퀴즈 내용")
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuizSet quizSet;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "quizQuestion",
            cascade = CascadeType.ALL, // 부모객체 저장, 삭제시 같이
            orphanRemoval = true // 참조가 없는 하위 객체 삭제
    )
    private List<QuizAnswer> answers = new ArrayList<>();
}
