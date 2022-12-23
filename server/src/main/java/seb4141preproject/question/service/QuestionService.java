package seb4141preproject.question.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seb4141preproject.question.entity.Question;
import seb4141preproject.question.entity.QuestionView;
import seb4141preproject.question.repository.QuestionRepository;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionVoteService questionVoteService;
//    private final AnswerService answerService;

    public Question createQuestion(Question question) {
        question.setQuestionView(new QuestionView());
        question.getQuestionView().setQuestion(question);

        return questionRepository.save(question);
    }

    @Transactional(readOnly = true)
    public Page<Question> readQuestions(int page, int size, String q) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Question> questions =
                q == null
                ? questionRepository.findAll(pageRequest)
                : questionRepository.findByQ(q, pageRequest);

        questions.forEach(question -> {
//            question.setAnswerCount(answerService.readAnswerCount(question.getId());
            question.setVoteCount(questionVoteService.readQuestionVoteCount(question.getId()));
        });

        return questions;
    }

    public Question readQuestion(long id) {
        Question question = questionRepository.findById(id).orElseThrow();
        question.getQuestionView().countView();
//        question.setAnswerCount(answerService.readAnswerCount(id);
        question.setVoteCount(questionVoteService.readQuestionVoteCount(id));

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
}
