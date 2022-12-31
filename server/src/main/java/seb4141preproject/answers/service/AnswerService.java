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
import seb4141preproject.members.entity.Member;
import seb4141preproject.questions.entity.Question;
import seb4141preproject.questions.repository.QuestionRepository;
import seb4141preproject.utils.ExceptionMessage;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@Transactional
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
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
                        new NoSuchElementException(ExceptionMessage.ANSWER_NOT_FOUND.get()));
        return findAnswer;
    }

    @Transactional(readOnly = true)
    public Question verifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion =
                questionRepository.findById(questionId);
        Question findQuestion =
                optionalQuestion.orElseThrow(()->
                        new NoSuchElementException(ExceptionMessage.ANSWER_NOT_FOUND.get()));
        return findQuestion;
    }

    public boolean checkMember(Member principal, long id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);

        return optionalAnswer.isPresent()
                && (optionalAnswer.get().getMember().getId() == principal.getId());
    }
}
