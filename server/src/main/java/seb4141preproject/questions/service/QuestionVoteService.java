package seb4141preproject.questions.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seb4141preproject.utils.Vote;
import seb4141preproject.questions.entity.QuestionVote;
import seb4141preproject.questions.repository.QuestionVoteRepository;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class QuestionVoteService {
    private final QuestionVoteRepository questionVoteRepository;

    public QuestionVote createQuestionVote(QuestionVote questionVote) {
        return questionVoteRepository.save(questionVote);
    }

    @Transactional(readOnly = true)
    public long readQuestionVoteCount(long questionId) {
        return questionVoteRepository.countByQuestion_IdAndVoteStatus(questionId, Vote.VoteStatus.UPVOTE)
                - questionVoteRepository.countByQuestion_IdAndVoteStatus(questionId, Vote.VoteStatus.DOWNVOTE);
    }

    @Transactional(readOnly = true)
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
