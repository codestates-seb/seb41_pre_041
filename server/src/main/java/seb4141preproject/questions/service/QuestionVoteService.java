package seb4141preproject.questions.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seb4141preproject.questions.entity.QuestionVote;
import seb4141preproject.questions.repository.QuestionVoteRepository;
import seb4141preproject.utils.Vote;

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
    public QuestionVote readQuestionVote(long questionId, long memberId) {
        return questionVoteRepository.findByQuestion_IdAndMember_Id(questionId, memberId).orElseThrow();
    }

    public QuestionVote updateQuestionVote(QuestionVote questionVote, long memberId) {
        QuestionVote foundQuestionVote =
                questionVoteRepository.findByQuestion_IdAndMember_Id(questionVote.getQuestion().getId(), memberId)
                        .orElseThrow();
        foundQuestionVote.setVoteStatus(questionVote.getVoteStatus());

        return questionVoteRepository.save(foundQuestionVote);
    }
}
