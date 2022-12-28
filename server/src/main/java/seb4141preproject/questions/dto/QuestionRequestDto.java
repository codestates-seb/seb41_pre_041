package seb4141preproject.questions.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class QuestionRequestDto {
    @NotNull
    @Size(min = 15, max = 150)
    private String title;

    @NotNull
    @Size(min = 30, max = 65535)
    private String content;
}
