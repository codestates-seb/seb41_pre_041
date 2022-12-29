package seb4141preproject.questions.dto;

import lombok.Getter;
import seb4141preproject.utils.Vote;

import javax.validation.constraints.NotNull;

@Getter
public class QuestionVoteRequestDto {

    @NotNull
    private Vote.VoteStatus voteStatus;
}
