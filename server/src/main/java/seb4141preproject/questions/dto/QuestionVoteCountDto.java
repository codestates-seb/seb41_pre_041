package seb4141preproject.questions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionVoteCountDto {

    private long questionId;

    private long voteCount;
}
