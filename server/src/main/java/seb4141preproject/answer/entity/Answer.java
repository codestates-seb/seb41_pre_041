package seb4141preproject.answer.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import seb4141preproject.Member;
import seb4141preproject.Question;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * H2로 테스트
 */


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(nullable = false)
    private String description;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    // 테스트를 위해 목업 데이터 클래스 연결
    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
