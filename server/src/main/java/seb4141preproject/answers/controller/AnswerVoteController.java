package seb4141preproject.answers.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seb4141preproject.answers.dto.AnswerVoteCountDto;
import seb4141preproject.answers.dto.AnswerVoteRequestDto;
import seb4141preproject.answers.dto.AnswerVoteResponseDto;
import seb4141preproject.answers.entity.AnswerVote;
import seb4141preproject.answers.mapper.AnswerVoteMapper;
import seb4141preproject.answers.service.AnswerVoteService;
import seb4141preproject.members.entity.Member;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/api/answers/{answer-id}")
@CrossOrigin
public class AnswerVoteController {
    private final AnswerVoteService answerVoteService;
    private final AnswerVoteMapper mapper;

    @PostMapping("/votes")
    public ResponseEntity<AnswerVoteResponseDto> postAnswerVote(@Positive @PathVariable("answer-id") long answerId,
                                                                @Valid @RequestBody AnswerVoteRequestDto requestDto) {
        AnswerVote answerVote = mapper.answerVoteRequestDtoToAnswerVote(requestDto, answerId);

        AnswerVote createdAnswerVote = answerVoteService. createAnswerVote(answerVote);

        return new ResponseEntity<>(mapper.answerVoteToAnswerVoteResponseDto(createdAnswerVote), HttpStatus.CREATED);
    }

    @GetMapping("/votes/count")
    public ResponseEntity<AnswerVoteCountDto> getAnswerVoteCount(@Positive @PathVariable("answer-id") long answerId){
        long voteCount = answerVoteService.readAnswerVoteCount(answerId);

        return new ResponseEntity<>(new AnswerVoteCountDto(answerId, voteCount), HttpStatus.OK);
    }

    @GetMapping("/votes/me")
    public ResponseEntity<AnswerVoteResponseDto> getAnswerVote(@Positive @PathVariable("answer-id") long answerId,
                                                               @AuthenticationPrincipal Member member) {
        AnswerVote answerVote = answerVoteService.readAnswerVote(answerId, member.getId());

        return new ResponseEntity<>(mapper.answerVoteToAnswerVoteResponseDto(answerVote), HttpStatus.OK);
    }

    @PatchMapping("/votes/me")
    public ResponseEntity<AnswerVoteResponseDto> patchAnswerVote(@Positive @PathVariable("answer-id") long answerId,
                                                                 @Valid @RequestBody AnswerVoteRequestDto requestDto,
                                                                 @AuthenticationPrincipal Member member){
        AnswerVote answerVote = mapper.answerVoteRequestDtoToAnswerVote(requestDto, answerId);

        AnswerVote updateAnswerVote = answerVoteService.updateAnswerVote(answerVote, member.getId());

        return new ResponseEntity<>(mapper.answerVoteToAnswerVoteResponseDto(updateAnswerVote), HttpStatus.OK);
    }
}

