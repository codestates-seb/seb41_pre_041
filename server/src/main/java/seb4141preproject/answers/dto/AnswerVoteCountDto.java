package seb4141preproject.answers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnswerVoteCountDto {
    private long answerId;
    private long voteCount;
}
