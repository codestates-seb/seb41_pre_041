package seb4141preproject.questions.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import seb4141preproject.questions.entity.QuestionResponse;

public interface QuestionResponseRepository extends JpaRepository<QuestionResponse, Long> {

    @Query("select x from QuestionResponse x where x.title like %?1% or x.content like %?1%")
    Page<QuestionResponse> findByQ(String q, Pageable pageable);
}
