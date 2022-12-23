package seb4141preproject.question.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import seb4141preproject.Member;
import seb4141preproject.Vote;
import seb4141preproject.question.entity.QuestionVote;
import seb4141preproject.question.repository.QuestionVoteRepository;

@Slf4j
@AllArgsConstructor
@Service
public class QuestionVoteService {
    private final QuestionVoteRepository questionVoteRepository;

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
