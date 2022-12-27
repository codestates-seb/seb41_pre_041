package seb4141preproject.answers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seb4141preproject.answers.entity.AnswerVote;
import seb4141preproject.utils.Vote;

import java.util.Optional;

public interface AnswerVoteRepository extends JpaRepository<AnswerVote, Long> {
    long countByAnswer_IdAndVoteStatus(long questionId, Vote.VoteStatus voteStatus);

    Optional<AnswerVote> findByAnswer_IdAndMember_Id(long answerId, long memberId);
}
