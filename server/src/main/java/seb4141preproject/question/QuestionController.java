package seb4141preproject.question;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seb4141preproject.Member;
import seb4141preproject.PaginatedResponseDto;
import seb4141preproject.question.dto.*;
import seb4141preproject.question.entity.Question;
import seb4141preproject.question.entity.QuestionVote;

import javax.validation.Valid;
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
    public ResponseEntity<PaginatedResponseDto<QuestionResponseDto>> getQuestions(
            @Positive @RequestParam int page,
            @Positive @RequestParam int size,
            @RequestParam(required = false) String q
    ) {
        Page<Question> questionPage = questionService.readQuestions(page - 1, size, q);

        return new ResponseEntity<>(
                new PaginatedResponseDto<>(
                        mapper.questionsToQuestionResponseDtos(questionPage.toList()),
                        new PaginatedResponseDto.PageInfo(
                                questionPage.getNumber() + 1,
                                questionPage.getSize(),
                                questionPage.getTotalElements(),
                                questionPage.getTotalPages()
                        )
                ),
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
        Question question = mapper.questionRequestDtoToQuestion(requestDto);
        question.setId(id);

        Question updatedQuestion = questionService.updateQuestion(question);

        return new ResponseEntity<>(mapper.questionToQuestionResponseDto(updatedQuestion), HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@Positive @PathVariable("question-id") long id) {
        questionService.deleteQuestion(id);
    }

    @PostMapping("/{question-id}/votes")
    public ResponseEntity<QuestionVoteResponseDto> postQuestionVote(
            @Positive @PathVariable("question-id") long questionId,
            @Valid @RequestBody QuestionVoteRequestDto requestDto
    ) {
        QuestionVote questionVote = mapper.questionVoteRequestDtoToQuestionVote(requestDto);
        questionVote.setQuestion(new Question());
        questionVote.getQuestion().setId(questionId);

        QuestionVote createdQuestionVote = questionService.createQuestionVote(questionVote);

        return new ResponseEntity<>(
                mapper.questionVoteToQuestionVoteResponseDto(createdQuestionVote),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{question-id}/votes/count")
    public ResponseEntity<QuestionVoteCountDto> getQuestionVoteCount(
            @Positive @PathVariable("question-id") long questionId
    ) {
        long voteCount = questionService.getQuestionVoteCount(questionId);

        return new ResponseEntity<>(new QuestionVoteCountDto(questionId, voteCount), HttpStatus.OK);
    }

    @GetMapping("/{question-id}/votes/{member-id}")
    public ResponseEntity<QuestionVoteResponseDto> getQuestionVote(
            @Positive @PathVariable("question-id") long questionId,
            @Positive @PathVariable("member-id") long memberId
    ) {
        QuestionVote questionVote = questionService.readQuestionVote(questionId, memberId);

        return new ResponseEntity<>(mapper.questionVoteToQuestionVoteResponseDto(questionVote), HttpStatus.OK);
    }

    @PatchMapping("/{question-id}/votes/{member-id}")
    public ResponseEntity<QuestionVoteResponseDto> patchQuestionVote(
            @Positive @PathVariable("question-id") long questionId,
            @Positive @PathVariable("member-id") long memberId,
            @Valid @RequestBody QuestionVoteRequestDto requestDto
    ) {
        QuestionVote questionVote = mapper.questionVoteRequestDtoToQuestionVote(requestDto);
        questionVote.setQuestion(new Question());
        questionVote.getQuestion().setId(questionId);
        questionVote.setMember(new Member());
        questionVote.getMember().setId(memberId);

        QuestionVote updatedQuestionVote = questionService.updateQuestionVote(questionVote);

        return new ResponseEntity<>(mapper.questionVoteToQuestionVoteResponseDto(updatedQuestionVote), HttpStatus.OK);
    }
}
