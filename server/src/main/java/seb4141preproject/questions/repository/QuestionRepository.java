package seb4141preproject.questions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seb4141preproject.questions.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
