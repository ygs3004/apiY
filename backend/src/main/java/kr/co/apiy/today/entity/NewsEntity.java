package kr.co.apiy.today.entity;

import jakarta.persistence.*;
import kr.co.apiy.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "NEWS")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 350)
    private String originalLink;

    @Column(length = 350)
    private String link;

    @Column(length = 1000)
    private String description;

    private LocalDateTime pubDate;

}
