package seb4141preproject.questions.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Subselect(
        "select q.id id, q.title title, q.content content, m.id member_id, m.name member_name, " +
                "q.created_at created_at, q.modified_at modified_at, qv.view_count view_count, " +
                "coalesce(ac.answer_count, 0) answer_count, " +
                "coalesce(quv.vote_count, 0) - coalesce(qdv.vote_count, 0) vote_count " +
                "from question q " +
                "join member m on m.id = q.member_id " +
                "join question_view qv on qv.id = q.id " +
                "left join (select question_id, count(*) answer_count from answer group by question_id) ac " +
                "on ac.question_id = q.id " +
                "left join (select question_id, count(*) vote_count from question_vote " +
                "group by question_id having vote_status = 1) quv on quv.question_id = q.id " +
                "left join (select question_id, count(*) vote_count from question_vote " +
                "group by question_id having vote_status = 2) qdv on qdv.question_id = q.id"
)
@Synchronize({"question", "member", "question_view", "answer", "question_vote"})
public class QuestionResponse {
    @Id
    private long id;
    private String title;
    private String content;
    private long memberId;
    private String memberName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private long viewCount;
    private long answerCount;
    private long voteCount;
}
