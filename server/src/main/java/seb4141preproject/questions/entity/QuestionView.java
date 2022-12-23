package seb4141preproject.questions.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class QuestionView {
    @Id
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Question question;

    private long viewCount;

    public void countView() {
        viewCount++;
    }
}
