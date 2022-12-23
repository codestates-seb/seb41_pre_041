package seb4141preproject.answers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class AnswerDto {
    @Getter
    @AllArgsConstructor
    public static class Post {

        private long memberId;

        private long questionId;
        @NotBlank
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        @NotBlank
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private long answerId;
        private long memberId;
        private long questionId;
        private String content;

        private LocalDateTime createAt;

        private LocalDateTime modifiedAt;


    }


}
