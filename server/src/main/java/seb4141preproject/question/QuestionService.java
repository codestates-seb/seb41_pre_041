package seb4141preproject.question;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ValueConstants;
import seb4141preproject.Member;

@Slf4j
@AllArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question createQuestion(Question question) {
        // Spring Security에서 인증된 principal을 받아와야 한다.
        // 인증 구현 전까지는 mock 사용
        Member member = new Member();
        member.setId(1L);
        member.setName("홍길동");
        question.setMember(member);

        return questionRepository.save(question);
    }

    public Page<Question> readQuestions(int page, int size, String q) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        return q.equals(ValueConstants.DEFAULT_NONE)
                ? questionRepository.findAll(pageRequest)
                : questionRepository.findByQ(q, pageRequest);
    }

    public Question readQuestion(long id) {
        Question question = questionRepository.findById(id).orElseThrow();
        question.countViews();

        return question;
    }

    public Question updateQuestion(Question question) {
        Question foundQuestion = questionRepository.findById(question.getId()).orElseThrow();
        foundQuestion.setTitle(question.getTitle());
        foundQuestion.setContent(question.getContent());

        return questionRepository.save(foundQuestion);
    }

    public void deleteQuestion(long id) {
        questionRepository.deleteById(id);
    }
}
