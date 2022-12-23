package seb4141preproject.questions.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class QuestionRequestDto {
    @NotNull
    private String title;

    @NotNull
    private String content;
}
