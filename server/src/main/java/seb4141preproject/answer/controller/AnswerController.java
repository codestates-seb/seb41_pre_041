package seb4141preproject.answer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seb4141preproject.answer.dto.AnswerDto;
import seb4141preproject.answer.entity.Answer;
import seb4141preproject.answer.mapper.AnswerMapper;
import seb4141preproject.answer.service.AnswerService;
import seb4141preproject.dto.MultiResponseDto;
import seb4141preproject.dto.SingleResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * - AnswerController_v1
 * Member/Question 연결 필요
 */

@RestController
@RequestMapping("/answer")
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

        return new ResponseEntity<>(
                new SingleResponseDto<>(response),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer(
            @PathVariable("answer-id") @Positive long answerId,
            @Valid @RequestBody AnswerDto.Patch requestBody) {
        Answer answer =
                answerService.updateAnswer(mapper.answerPatchToAnswer(requestBody));
        AnswerDto.Response response = mapper.answerToAnswerResponse(answer);
        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{answer-id}")
    public ResponseEntity getAnswer(
            @PathVariable("answer-id") @Positive long answerId) {
        Answer answer = answerService.findAnswer(answerId);
        AnswerDto.Response response = mapper.answerToAnswerResponse(answer);
        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAnswers(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<Answer> pageAnswers = answerService.findAnswers(page - 1, size);
        List<Answer> answers = pageAnswers.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.answersToAnswerResponses(answers),pageAnswers),
                HttpStatus.OK);
    }

    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(
            @PathVariable("answer-id") @Positive long answerId) {
        answerService.deleteAnswer(answerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
