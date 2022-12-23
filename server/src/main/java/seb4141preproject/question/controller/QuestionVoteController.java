package seb4141preproject.question.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seb4141preproject.question.service.QuestionVoteService;
import seb4141preproject.question.dto.QuestionVoteCountDto;
import seb4141preproject.question.dto.QuestionVoteRequestDto;
import seb4141preproject.question.dto.QuestionVoteResponseDto;
import seb4141preproject.question.entity.Question;
import seb4141preproject.question.entity.QuestionVote;
import seb4141preproject.question.mapper.QuestionVoteMapper;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/api/questions/{question-id}")
public class QuestionVoteController {
    private final QuestionVoteService questionVoteService;
    private final QuestionVoteMapper mapper;

    @PostMapping("/votes")
    public ResponseEntity<QuestionVoteResponseDto> postQuestionVote(
            @Positive @PathVariable("question-id") long questionId,
            @Valid @RequestBody QuestionVoteRequestDto requestDto
    ) {
        QuestionVote questionVote = mapper.questionVoteRequestDtoToQuestionVote(requestDto);
        questionVote.setQuestion(new Question());
        questionVote.getQuestion().setId(questionId);

        QuestionVote createdQuestionVote = questionVoteService.createQuestionVote(questionVote);

        return new ResponseEntity<>(
                mapper.questionVoteToQuestionVoteResponseDto(createdQuestionVote),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/votes/count")
    public ResponseEntity<QuestionVoteCountDto> getQuestionVoteCount(
            @Positive @PathVariable("question-id") long questionId
    ) {
        long voteCount = questionVoteService.readQuestionVoteCount(questionId);

        return new ResponseEntity<>(new QuestionVoteCountDto(questionId, voteCount), HttpStatus.OK);
    }

    @GetMapping("/votes/me")
    public ResponseEntity<QuestionVoteResponseDto> getQuestionVote(
            @Positive @PathVariable("question-id") long questionId
    ) {
        QuestionVote questionVote = questionVoteService.readQuestionVote(questionId);

        return new ResponseEntity<>(mapper.questionVoteToQuestionVoteResponseDto(questionVote), HttpStatus.OK);
    }

    @PatchMapping("/votes/me")
    public ResponseEntity<QuestionVoteResponseDto> patchQuestionVote(
            @Positive @PathVariable("question-id") long questionId,
            @Valid @RequestBody QuestionVoteRequestDto requestDto
    ) {
        QuestionVote questionVote = mapper.questionVoteRequestDtoToQuestionVote(requestDto);
        questionVote.setQuestion(new Question());
        questionVote.getQuestion().setId(questionId);

        QuestionVote updatedQuestionVote = questionVoteService.updateQuestionVote(questionVote);

        return new ResponseEntity<>(mapper.questionVoteToQuestionVoteResponseDto(updatedQuestionVote), HttpStatus.OK);
    }
}
