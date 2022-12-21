package seb4141preproject.question;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seb4141preproject.PaginatedResponseDto;
import seb4141preproject.question.dto.QuestionRequestDto;
import seb4141preproject.question.dto.QuestionResponseDto;

import java.time.LocalDateTime;
import java.util.List;

//@RestController
//@RequestMapping("/api/questions")
public class QuestionMockController {
    private final QuestionResponseDto mockQuestionResponseDto =
            new QuestionResponseDto(
                    1L,
                    "청춘예찬",
                    "싶이 밝은 청춘의 사는가 따뜻한 힘차게 사막이다. 있음으로써 길을 바이며, 넣는 같은 구하기 아름다우냐? 때에, " +
                            "힘차게 부패를 곳으로 없는 가지에 천하를 인도하겠다는 할지라도 것이다. 귀는 모래뿐일 평화스러운 예가 위하여서 곧 과실이 " +
                            "이것을 청춘의 말이다. 그들은 있는 많이 황금시대다. 오직 살 인류의 피가 모래뿐일 따뜻한 내려온 것이다. 피가 청춘 내려온 " +
                            "살았으며, 온갖 약동하다.\n" +
                            "\n" +
                            "끓는 방황하여도, 크고 동력은 귀는 사막이다. 용기가 있을 속에서 크고 할지라도 것이다. 인간이 모래뿐일 찾아다녀도, 풀이 유" +
                            "소년에게서 옷을 것이다. 주며, 사람은 대중을 구하지 얼음이 아니더면, 때문이다. 있는 꾸며 새 원대하고, 작고 부패를 시들어 " +
                            "보이는 사랑의 이것이다.\n" +
                            "\n" +
                            "싸인 기관과 힘차게 교향악이다. 밝은 불어 얼마나 이상을 불어 같이, 귀는 아니다. 소담스러운 거친 방황하였으며, " +
                            "품었기 이상의 열매를 것이 날카로우나 가지에 있으랴? 능히 뜨고, 무엇이 이상은 피부가 원질이 모래뿐일 보이는 있는가? " +
                            "가장 예가 크고 꽃 그와 위하여 않는 천자만홍이 교향악이다. 밥을 같은 고행을 교향악이다.",
                    1L,
                    "홍길동",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    0
            );

    @PostMapping
    public ResponseEntity<QuestionResponseDto> postQuestion(@RequestBody QuestionRequestDto requestDto) {
        return new ResponseEntity<>(mockQuestionResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDto<QuestionResponseDto>> getQuestions(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String q
    ) {
        return new ResponseEntity<>(
                new PaginatedResponseDto<>(
                        List.of(mockQuestionResponseDto),
                        new PaginatedResponseDto.PageInfo(1, 15, 1, 1)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{question-id}")
    public ResponseEntity<QuestionResponseDto> getQuestion(@PathVariable("question-id") long id) {
        return new ResponseEntity<>(mockQuestionResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity<QuestionResponseDto> patchQuestion(@PathVariable("question-id") long id,
                                                             @RequestBody QuestionRequestDto requestDto) {
        return new ResponseEntity<>(mockQuestionResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@PathVariable("question-id") long id) {
    }
}
