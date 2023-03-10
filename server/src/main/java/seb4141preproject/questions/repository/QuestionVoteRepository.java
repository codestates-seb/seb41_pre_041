package seb4141preproject.questions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seb4141preproject.questions.entity.QuestionVote;
import seb4141preproject.utils.Vote;

import java.util.Optional;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {

    long countByQuestion_IdAndVoteStatus(long questionId, Vote.VoteStatus voteStatus);

    Optional<QuestionVote> findByQuestion_IdAndMember_Id(long questionId, long memberId);
}
