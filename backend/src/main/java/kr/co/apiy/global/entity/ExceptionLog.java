package kr.co.apiy.global.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EXCEPTION_LOG")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int errorCode;

    private String errorMessage;

}
