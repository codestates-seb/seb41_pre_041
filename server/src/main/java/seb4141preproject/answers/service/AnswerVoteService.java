package seb4141preproject.answers.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seb4141preproject.answers.entity.AnswerVote;
import seb4141preproject.answers.repository.AnswerRepository;
import seb4141preproject.answers.repository.AnswerVoteRepository;
import seb4141preproject.utils.ExceptionMessage;
import seb4141preproject.utils.Vote;

import java.util.NoSuchElementException;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AnswerVoteService {
    private final AnswerVoteRepository answerVoteRepository;

    private final AnswerRepository answerRepository;

    public AnswerVote createAnswerVote(AnswerVote answerVote) {
        return answerVoteRepository.save(answerVote);
    }

    @Transactional(readOnly = true)
    public long readAnswerVoteCount(long answerId) {
        if (!answerRepository.existsById(answerId)) {
            throw new NoSuchElementException(ExceptionMessage.ANSWER_NOT_FOUND.get());
        }

        return answerVoteRepository.countByAnswer_IdAndVoteStatus(answerId, Vote.VoteStatus.UPVOTE)
                - answerVoteRepository.countByAnswer_IdAndVoteStatus(answerId, Vote.VoteStatus.DOWNVOTE);
    }

    @Transactional(readOnly = true)
    public AnswerVote readAnswerVote(long answerId, long memberId) {
        return answerVoteRepository.findByAnswer_IdAndMember_Id(answerId, memberId)
                .orElseThrow(() -> new NoSuchElementException(ExceptionMessage.VOTE_NOT_FOUND.get()));
    }

    public AnswerVote updateAnswerVote(AnswerVote answerVote, long memberId) {
        AnswerVote foundAnswerVote =
                answerVoteRepository.findByAnswer_IdAndMember_Id(answerVote.getAnswer().getId(), memberId)
                        .orElseThrow(() -> new NoSuchElementException(ExceptionMessage.VOTE_NOT_FOUND.get()));
        foundAnswerVote.setVoteStatus(answerVote.getVoteStatus());

        return answerVoteRepository.save(foundAnswerVote);
    }
}
