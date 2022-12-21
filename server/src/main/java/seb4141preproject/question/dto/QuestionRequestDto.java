package seb4141preproject.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class QuestionRequestDto {
    @NotNull
    private final String title;
    @NotNull
    private final String content;
}
