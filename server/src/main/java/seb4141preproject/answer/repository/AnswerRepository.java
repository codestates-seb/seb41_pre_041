package seb4141preproject.answer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import seb4141preproject.answer.entity.Answer;
import seb4141preproject.dto.PageInfo;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    //TODO : query 확인 필요
    @Query(value = "select a from Question a where a.id = :questionId")
    Page<Answer> findByQuestionId(long questionId, PageRequest pageRequest);
}
