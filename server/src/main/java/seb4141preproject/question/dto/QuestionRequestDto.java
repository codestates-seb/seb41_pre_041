package seb4141preproject.question.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class QuestionRequestDto {
    @NotNull
    private String title;

    @NotNull
    private String content;
}
