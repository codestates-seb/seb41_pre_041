package seb4141preproject.question.dto;

import lombok.Getter;
import seb4141preproject.Vote;

import javax.validation.constraints.NotNull;

@Getter
public class QuestionVoteRequestDto {
    @NotNull
    private Vote.VoteStatus voteStatus;
}
