package seb4141preproject.utils;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class Vote extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated
    @Column(nullable = false)
    private VoteStatus voteStatus = VoteStatus.NO_VOTE;

    public enum VoteStatus {NO_VOTE, UPVOTE, DOWNVOTE}
}
