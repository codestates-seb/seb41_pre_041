package seb4141preproject.answers.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import seb4141preproject.utils.Vote;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"MEMBER_ID", "ANSWER_ID"}))
public class AnswerVote extends Vote {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANSWER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Answer answer;
}
