package seb4141preproject.answers.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import seb4141preproject.answers.entity.Answer;
import seb4141preproject.answers.repository.AnswerRepository;
import seb4141preproject.answers.repository.AnswerVoteRepository;
import seb4141preproject.members.entity.Member;
import seb4141preproject.questions.entity.Question;
import seb4141preproject.questions.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    private final AnswerVoteRepository answerVoteRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, AnswerVoteRepository answerVoteRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.answerVoteRepository = answerVoteRepository;
    }

    public Answer createAnswer(Answer answer) {
        Question findquestion = verifiedQuestion(answer.getQuestion().getId());

        return answerRepository.save(answer);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Answer updateAnswer(Answer answer) {
        Answer findAnswer = findVerifiedAnswer(answer.getId());

        Optional.ofNullable(answer.getContent())
                .ifPresent(content -> findAnswer.setContent(content));

        return answerRepository.save(findAnswer);
    }

    public Answer findAnswer(long answerId) {
        return findVerifiedAnswer(answerId);
    }

    @Transactional(readOnly = true)
    public Page<Answer> findAnswers(int page, int size, long questionId, Sort sort) {

        return answerRepository.findByQuestionId(questionId, PageRequest.of(page, size),sort);
    }

    public void deleteAnswer(long answerId) {
        Answer findAnswer = findVerifiedAnswer(answerId);

        answerRepository.delete(findAnswer);
    }

    public Answer findVerifiedAnswer(long answerId) {
        Optional<Answer> optionalAnswer =
                answerRepository.findById(answerId);
        Answer findAnswer =
                optionalAnswer.orElseThrow(() ->
                        new IllegalArgumentException("답변을 찾을 수 없습니다."));
        return findAnswer;
    }

    @Transactional(readOnly = true)
    public Question verifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion =
                questionRepository.findById(questionId);
        Question findQuestion =
                optionalQuestion.orElseThrow(()->
                        new IllegalArgumentException("질문을 찾을 수 없습니다."));
        return findQuestion;
    }

}
