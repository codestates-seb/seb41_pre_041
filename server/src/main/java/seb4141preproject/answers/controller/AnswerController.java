package seb4141preproject.answers.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seb4141preproject.answers.dto.AnswerDto;
import seb4141preproject.answers.entity.Answer;
import seb4141preproject.answers.mapper.AnswerMapper;
import seb4141preproject.answers.service.AnswerService;
import seb4141preproject.dto.MultiResponseDto;
import seb4141preproject.dto.SingleResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * - AnswerController_v1
 * - Member/Question 연결 필요
 * - getAnswers 응답 구조 테스트 필요
 */

@RestController
@RequestMapping("/api/answers")
@Validated
@Slf4j
public class AnswerController {
    private final AnswerService answerService;
    private final AnswerMapper mapper;

    public AnswerController(AnswerService answerService, AnswerMapper mapper) {
        this.answerService = answerService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postAnswer(@Valid @RequestBody AnswerDto.Post requestBody) {
        Answer answer = mapper.answerPostToAnswer(requestBody);

        Answer createdAnswer = answerService.createAnswer(answer);
        AnswerDto.Response response = mapper.answerToAnswerResponse(createdAnswer);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer(
            @PathVariable("answer-id") @Positive long answerId,
            @Valid @RequestBody AnswerDto.Patch requestBody) {
        Answer answer =
                answerService.updateAnswer(mapper.answerPatchToAnswer(requestBody));
        AnswerDto.Response response = mapper.answerToAnswerResponse(answer);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{answer-id}")
    public ResponseEntity getAnswer(
            @PathVariable("answer-id") @Positive long answerId) {
        Answer answer = answerService.findAnswer(answerId);
        AnswerDto.Response response = mapper.answerToAnswerResponse(answer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAnswers(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size,
                                     @Positive @RequestParam long questionId) {
        Page<Answer> answers = answerService.findAnswers(page - 1, size, questionId);
        List<Answer> answerList = answers.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.answersToAnswerResponses(answerList),answers),
                        HttpStatus.OK);
    }

    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(
            @PathVariable("answer-id") @Positive long answerId) {
        answerService.deleteAnswer(answerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}