package seb4141preproject.question.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import seb4141preproject.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String content;

    private long viewCount;

    // 답변 수와 투표 수를 임시 저장하기 위해 사용
    @Transient
    private long answerCount;

    @Transient
    private long voteCount;

    // Spring Security authentication 구현 후 @CreatedBy auditing 기능으로 입력 예정
    // 구현 전에는 수동으로 입력
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public void countView() {
        viewCount++;
    }
}