package seb4141preproject.questions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import seb4141preproject.utils.Vote;

@Getter
@AllArgsConstructor
public class QuestionVoteResponseDto {

    private long id;

    private long questionId;

    private long memberId;

    private Vote.VoteStatus voteStatus;
}
