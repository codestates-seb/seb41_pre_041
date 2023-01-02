package seb4141preproject.questions.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seb4141preproject.members.entity.Member;
import seb4141preproject.questions.entity.Question;
import seb4141preproject.questions.entity.QuestionResponse;
import seb4141preproject.questions.entity.QuestionView;
import seb4141preproject.questions.repository.QuestionRepository;
import seb4141preproject.questions.repository.QuestionResponseRepository;
import seb4141preproject.utils.ExceptionMessage;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionResponseRepository questionDetailRepository;

    public Question createQuestion(Question question) {
        question.setQuestionView(new QuestionView());
        question.getQuestionView().setQuestion(question);

        return questionRepository.save(question);
    }

    @Transactional(readOnly = true)
    public Page<QuestionResponse> readQuestions(int page, int size, Sort sort, String q) {
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        return Optional.ofNullable(q)
                .map(string -> questionDetailRepository.findByQ(string, pageable))
                .orElse(questionDetailRepository.findAll(pageable));
    }

    public Question readQuestion(long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(ExceptionMessage.QUESTION_NOT_FOUND.get()));
        question.getQuestionView().countView();

        return question;
    }

    public Question updateQuestion(Question question) {
        Question foundQuestion = questionRepository.findById(question.getId())
                .orElseThrow(() -> new NoSuchElementException(ExceptionMessage.QUESTION_NOT_FOUND.get()));
        foundQuestion.setTitle(question.getTitle());
        foundQuestion.setContent(question.getContent());

        return foundQuestion;
    }

    public void deleteQuestion(long id) {
        questionRepository.deleteById(id);
    }

    public boolean checkMember(Member principal, long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);

        return optionalQuestion.isPresent()
                && (optionalQuestion.get().getMember().getId() == principal.getId());
    }
}
