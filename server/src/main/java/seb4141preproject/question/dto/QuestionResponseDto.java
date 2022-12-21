package seb4141preproject.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class QuestionResponseDto {
    private final long id;
    private final String title;
    private final String content;
    private final long memberId;
    private final String memberName;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final int views;
}
