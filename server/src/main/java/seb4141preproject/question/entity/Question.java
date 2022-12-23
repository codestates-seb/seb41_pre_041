package seb4141preproject.question.entity;

import lombok.Getter;
import lombok.Setter;
import seb4141preproject.entity.Auditable;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Question extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String content;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    private QuestionView questionView;

    // 답변 수와 투표 수를 임시 저장하기 위해 사용
    @Transient
    private long answerCount;

    @Transient
    private long voteCount;
}