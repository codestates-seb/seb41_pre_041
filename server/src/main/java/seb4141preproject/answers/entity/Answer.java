package seb4141preproject.answers.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import seb4141preproject.members.entity.Member;
import seb4141preproject.questions.entity.Question;
import seb4141preproject.utils.Auditable;

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
public class Answer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    // 글자 수 지정
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

//    @ManyToOne
//    @JoinColumn(name = "MEMBER_ID")
//    private Member member;
}
