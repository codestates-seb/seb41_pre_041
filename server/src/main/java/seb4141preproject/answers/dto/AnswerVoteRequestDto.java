package seb4141preproject.answers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seb4141preproject.utils.Vote;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerVoteRequestDto {
    @NotNull
    private Vote.VoteStatus voteStatus;
}
