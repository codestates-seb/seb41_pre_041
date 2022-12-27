package seb4141preproject.questions.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seb4141preproject.answers.repository.AnswerRepository;
import seb4141preproject.questions.entity.Question;
import seb4141preproject.questions.entity.QuestionView;
import seb4141preproject.questions.repository.QuestionRepository;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionVoteService questionVoteService;
    private final AnswerRepository answerRepository;

    public Question createQuestion(Question question) {
        question.setQuestionView(new QuestionView());
        question.getQuestionView().setQuestion(question);

        return questionRepository.save(question);
    }

    @Transactional(readOnly = true)
    public Page<Question> readQuestions(Pageable pageable, String q) {
        return q == null
                ? questionRepository.findAll(pageable)
                : questionRepository.findByQ(q, pageable);
    }

    public Question readQuestion(long id) {
        Question question = questionRepository.findById(id).orElseThrow();
        question.getQuestionView().countView();

        return question;
    }

    public Question updateQuestion(Question question) {
        Question foundQuestion = questionRepository.findById(question.getId()).orElseThrow();
        foundQuestion.setTitle(question.getTitle());
        foundQuestion.setContent(question.getContent());

        return questionRepository.save(foundQuestion);
    }

    public void deleteQuestion(long id) {
        questionRepository.deleteById(id);
    }

    public boolean checkMember(Authentication authentication, long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);

        return optionalQuestion.isPresent()
                && optionalQuestion.get().getMember().getEmail().equals(authentication.getName());
    }
}
