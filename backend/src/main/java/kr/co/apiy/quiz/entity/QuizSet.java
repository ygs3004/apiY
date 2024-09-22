package kr.co.apiy.quiz.entity;

import jakarta.persistence.*;
import kr.co.apiy.global.converters.Converters;
import kr.co.apiy.global.entity.BaseEntity;
import kr.co.apiy.quiz.dto.enums.QuizCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "QUIZ_SET")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuizSet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = Converters.ForQuizCategory.class)
    private QuizCategory category;

    @Column(nullable = false)
    private String subject;

}
