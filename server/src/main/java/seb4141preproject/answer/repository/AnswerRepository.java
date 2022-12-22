package seb4141preproject.answer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seb4141preproject.answer.entity.Answer;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findById(long answerId);
}
