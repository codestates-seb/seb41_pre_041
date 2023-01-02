package seb4141preproject.answers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import seb4141preproject.utils.Vote;

@Getter
@AllArgsConstructor
public class AnswerVoteResponseDto {
    private long id;
    private long answerId;
    private long memberId;
    private Vote.VoteStatus voteStatus;
}
