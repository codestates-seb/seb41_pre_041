package seb4141preproject.questions.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import seb4141preproject.questions.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("select x from Question x where x.title like %?1% or x.content like %?1%")
    Page<Question> findByQ(String q, Pageable pageable);
}
