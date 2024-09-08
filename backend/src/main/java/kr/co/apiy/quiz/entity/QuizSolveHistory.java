package kr.co.apiy.quiz.entity;

import jakarta.persistence.*;
import kr.co.apiy.auth.entity.Member;
import kr.co.apiy.global.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "QUIZ_SOLVE_HISTORY")
@Comment("퀴즈 풀이 기록")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"quizSet", "member"})
public class QuizSolveHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("퀴즈 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUIZ_SET_ID")
    QuizSet quizSet;

    @Comment("퀴즈 풀이 유저")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    Member member;

    @Comment("문제 수")
    @Column(nullable = false)
    Integer questionCount;
    
    @Comment("정답 수")
    @Column(nullable = false)
    Integer correctCount;

}
