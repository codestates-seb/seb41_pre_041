package seb4141preproject;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Auditing 방식으로 바뀔 예정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Enumerated
    private VoteStatus voteStatus = VoteStatus.NO_VOTE;

    public enum VoteStatus {NO_VOTE, UPVOTE, DOWNVOTE}
}
