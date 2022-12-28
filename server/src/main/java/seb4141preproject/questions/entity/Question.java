package seb4141preproject.questions.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import seb4141preproject.utils.Auditable;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Question extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Type(type = "text")
    private String content;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    private QuestionView questionView;

    @Formula("(select count(*) from answer a where a.question_id = id)")
    private long answerCount;

    @Formula(
            "(select count(*) from question_vote qv where qv.question_id = id and qv.vote_status = 1) - " +
                    "(select count(*) from question_vote qv where qv.question_id = id and qv.vote_status = 2)"
    )
    private long voteCount;
}