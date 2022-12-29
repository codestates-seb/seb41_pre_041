package seb4141preproject.answers.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import seb4141preproject.answers.entity.Answer;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findByQuestionId(long questionId, PageRequest pageRequest,Sort sort);

    long countByQuestion_Id(long questionId);
}