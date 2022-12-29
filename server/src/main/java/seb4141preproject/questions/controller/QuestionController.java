package seb4141preproject.questions.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seb4141preproject.questions.dto.QuestionRequestDto;
import seb4141preproject.questions.entity.Question;
import seb4141preproject.questions.entity.QuestionResponse;
import seb4141preproject.questions.mapper.QuestionMapper;
import seb4141preproject.questions.service.QuestionService;
import seb4141preproject.utils.MultiResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/api/questions")
@CrossOrigin
public class QuestionController {

    private final QuestionService questionService;

    private final QuestionMapper mapper;

    @PostMapping
    public ResponseEntity<QuestionResponse> postQuestion(@Valid @RequestBody QuestionRequestDto requestDto) {
        Question question = questionService.createQuestion(mapper.questionRequestDtoToQuestion(requestDto));

        return new ResponseEntity<>(mapper.questionToQuestionResponse(question), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<MultiResponseDto<QuestionResponse>> getQuestions(
            @Positive @RequestParam(defaultValue = "1") int page,
            @Positive @RequestParam(defaultValue = "15") int size,
            @SortDefault(sort = "id", direction = Sort.Direction.DESC) Sort sort,
            @Size(min = 1, max = 65535) @RequestParam(required = false) String q
    ) {
        Page<QuestionResponse> questionDetailPage = questionService.readQuestions(page, size, sort, q);

        return new ResponseEntity<>(
                new MultiResponseDto<>(questionDetailPage.toList(), questionDetailPage),
                HttpStatus.OK
        );
    }

    @GetMapping("/{question-id}")
    public ResponseEntity<QuestionResponse> getQuestion(@Positive @PathVariable("question-id") long id) {
        Question question = questionService.readQuestion(id);

        return new ResponseEntity<>(mapper.questionToQuestionResponse(question), HttpStatus.OK);
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity<QuestionResponse> patchQuestion(@Positive @PathVariable("question-id") long id,
                                                          @Valid @RequestBody QuestionRequestDto requestDto) {
        Question question = mapper.questionRequestDtoToQuestion(requestDto, id);

        Question updatedQuestion = questionService.updateQuestion(question);

        return new ResponseEntity<>(mapper.questionToQuestionResponse(updatedQuestion), HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@Positive @PathVariable("question-id") long id) {
        questionService.deleteQuestion(id);
    }
}
