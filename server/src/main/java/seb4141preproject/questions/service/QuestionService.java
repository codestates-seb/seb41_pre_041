package seb4141preproject.questions.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public Question createQuestion(Question question) {
        question.setQuestionView(new QuestionView());
        question.getQuestionView().setQuestion(question);

        return questionRepository.save(question);
    }

    @Transactional(readOnly = true)
    public Page<Question> readQuestions(int page, int size, Sort sort, String q) {
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        return Optional.ofNullable(q)
                .map(string -> questionRepository.findByQ(string, pageable))
                .orElse(questionRepository.findAll(pageable));
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
