package seb4141preproject.answers.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import seb4141preproject.answers.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    //TODO : query 확인 필요
    @Query(value = "select a from Question a where a.id = :questionId")
    Page<Answer> findByQuestionId(long questionId, PageRequest pageRequest);
}