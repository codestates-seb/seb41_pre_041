package seb4141preproject.question;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import seb4141preproject.Member;
import seb4141preproject.Vote;
import seb4141preproject.question.entity.Question;
import seb4141preproject.question.entity.QuestionVote;
import seb4141preproject.question.repository.QuestionRepository;
import seb4141preproject.question.repository.QuestionVoteRepository;

@Slf4j
@AllArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionVoteRepository questionVoteRepository;

    public Question createQuestion(Question question) {
        // Spring Security에서 인증된 principal을 받아와야 한다.
        // 인증 구현 전까지는 mock 사용
        question.setMember(new Member());
        question.getMember().setId(1L);

        return questionRepository.save(question);
    }

    public Page<Question> readQuestions(int page, int size, String q) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Question> questions =
                q == null
                ? questionRepository.findAll(pageRequest)
                : questionRepository.findByQ(q, pageRequest);

        questions.forEach(question -> {
//            question.setAnswerCount(getAnswerCount(question.getId());
            question.setVoteCount(getQuestionVoteCount(question.getId()));
        });

        return questions;
    }

    public Question readQuestion(long id) {
        Question question = questionRepository.findById(id).orElseThrow();
        question.countView();
        question.setVoteCount(getQuestionVoteCount(id));

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

    public QuestionVote createQuestionVote(QuestionVote questionVote) {
        // Spring Security에서 인증된 principal을 받아와야 한다.
        // 인증 구현 전까지는 mock 사용
        questionVote.setMember(new Member());
        questionVote.getMember().setId(1L);

        return questionVoteRepository.save(questionVote);
    }

    public long getQuestionVoteCount(long questionId) {
        return questionVoteRepository.countByQuestion_IdAndVoteStatus(questionId, Vote.VoteStatus.UPVOTE)
                - questionVoteRepository.countByQuestion_IdAndVoteStatus(questionId, Vote.VoteStatus.DOWNVOTE);
    }

    public QuestionVote readQuestionVote(long questionId) {
        // Spring Security에서 인증된 principal을 받아와야 한다.
        // 인증 구현 전까지는 mock 사용
        long memberId = 1L;

        return questionVoteRepository.findByQuestion_IdAndMember_Id(questionId, memberId).orElseThrow();
    }

    public QuestionVote updateQuestionVote(QuestionVote questionVote) {
        // Spring Security에서 인증된 principal을 받아와야 한다.
        // 인증 구현 전까지는 mock 사용
        long memberId = 1L;

        QuestionVote foundQuestionVote =
                questionVoteRepository.findByQuestion_IdAndMember_Id(questionVote.getQuestion().getId(), memberId)
                        .orElseThrow();
        foundQuestionVote.setVoteStatus(questionVote.getVoteStatus());

        return questionVoteRepository.save(foundQuestionVote);
    }
}
