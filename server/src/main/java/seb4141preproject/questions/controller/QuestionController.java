package seb4141preproject.questions.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seb4141preproject.questions.dto.QuestionRequestDto;
import seb4141preproject.questions.dto.QuestionResponseDto;
import seb4141preproject.questions.entity.Question;
import seb4141preproject.questions.mapper.QuestionMapper;
import seb4141preproject.questions.service.QuestionService;
import seb4141preproject.utils.MultiResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper mapper;

    @PostMapping
    public ResponseEntity<QuestionResponseDto> postQuestion(@Valid @RequestBody QuestionRequestDto requestDto) {
        Question question = questionService.createQuestion(mapper.questionRequestDtoToQuestion(requestDto));

        return new ResponseEntity<>(mapper.questionToQuestionResponseDto(question), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<MultiResponseDto<QuestionResponseDto>> getQuestions(
            @Positive @RequestParam(defaultValue = "1") int page,
            @Positive @RequestParam(defaultValue = "15") int size,
            Sort sort,
            @Min(1) @RequestParam(required = false) String q
    ) {
        Page<Question> questionPage = questionService.readQuestions(page, size, sort, q);

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.questionsToQuestionResponseDtos(questionPage.toList()), questionPage),
                HttpStatus.OK
        );
    }

    @GetMapping("/{question-id}")
    public ResponseEntity<QuestionResponseDto> getQuestion(@Positive @PathVariable("question-id") long id) {
        Question question = questionService.readQuestion(id);

        return new ResponseEntity<>(mapper.questionToQuestionResponseDto(question), HttpStatus.OK);
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity<QuestionResponseDto> patchQuestion(@Positive @PathVariable("question-id") long id,
                                                             @Valid @RequestBody QuestionRequestDto requestDto) {
        Question question = mapper.questionRequestDtoToQuestion(requestDto, id);

        Question updatedQuestion = questionService.updateQuestion(question);

        return new ResponseEntity<>(mapper.questionToQuestionResponseDto(updatedQuestion), HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@Positive @PathVariable("question-id") long id) {
        questionService.deleteQuestion(id);
    }
}
