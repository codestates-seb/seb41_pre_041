package seb4141preproject.answers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class AnswerDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        private long questionId;
        @NotBlank
        @Size(min = 30)
        private String content;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {
        private long id;
        @Size(min = 30)
        private String content;

        public void setId(long id) {
            this.id = id;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        long id;
        long memberId;
        long questionId;
        String memberName;
        String content;
        long voteCount;
        LocalDateTime createdAt;
        LocalDateTime modifiedAt;


    }


}
