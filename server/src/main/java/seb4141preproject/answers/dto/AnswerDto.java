package seb4141preproject.answers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class AnswerDto {
    @Getter
    @AllArgsConstructor
    public static class Post {

        // private long memberId;

        private long questionId;
        @NotBlank
        private String content;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {
        private long answerId;
        private String content;

        public void setAnswerId(long answerId) {
            this.answerId = answerId;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        long answerId;
        long memberId;
        long questionId;
        String content;

        LocalDateTime createdAt;

        LocalDateTime modifiedAt;


    }


}
