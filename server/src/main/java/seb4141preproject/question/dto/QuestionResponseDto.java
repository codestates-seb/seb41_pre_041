package seb4141preproject.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class QuestionResponseDto {
    long id;
    String title;
    String content;
    long memberId;
    String memberName;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    long viewCount;
    long answerCount;
    long voteCount;
}
