package seb4141preproject.questions.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seb4141preproject.members.entity.Member;
import seb4141preproject.questions.dto.QuestionVoteCountDto;
import seb4141preproject.questions.dto.QuestionVoteRequestDto;
import seb4141preproject.questions.dto.QuestionVoteResponseDto;
import seb4141preproject.questions.entity.QuestionVote;
import seb4141preproject.questions.mapper.QuestionVoteMapper;
import seb4141preproject.questions.service.QuestionVoteService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/api/questions/{question-id}")
@CrossOrigin
public class QuestionVoteController {

    private final QuestionVoteService questionVoteService;

    private final QuestionVoteMapper mapper;

    @PostMapping("/votes")
    public ResponseEntity<QuestionVoteResponseDto> postQuestionVote(
            @Positive @PathVariable("question-id") long questionId,
            @Valid @RequestBody QuestionVoteRequestDto requestDto
    ) {
        QuestionVote questionVote = mapper.questionVoteRequestDtoToQuestionVote(requestDto, questionId);

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
            @Positive @PathVariable("question-id") long questionId,
            @AuthenticationPrincipal Member member
    ) {
        QuestionVote questionVote = questionVoteService.readQuestionVote(questionId, member.getId());

        return new ResponseEntity<>(mapper.questionVoteToQuestionVoteResponseDto(questionVote), HttpStatus.OK);
    }

    @PatchMapping("/votes/me")
    public ResponseEntity<QuestionVoteResponseDto> patchQuestionVote(
            @Positive @PathVariable("question-id") long questionId,
            @Valid @RequestBody QuestionVoteRequestDto requestDto,
            @AuthenticationPrincipal Member member
    ) {
        QuestionVote questionVote = mapper.questionVoteRequestDtoToQuestionVote(requestDto, questionId);

        QuestionVote updatedQuestionVote = questionVoteService.updateQuestionVote(questionVote, member.getId());

        return new ResponseEntity<>(mapper.questionVoteToQuestionVoteResponseDto(updatedQuestionVote), HttpStatus.OK);
    }
}
