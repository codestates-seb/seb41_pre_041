package seb4141preproject.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class AnswerDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        private long memberId;
        @NotBlank
        private long questionId;
        @NotBlank
        private String description;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        @NotBlank
        private String description;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private long answerId;
        private long memberId;
        private long questionId;
        private String description;

        private LocalDateTime createAt;

        private LocalDateTime modifiedAt;


    }


}
