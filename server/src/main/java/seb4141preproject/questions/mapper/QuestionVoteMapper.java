package seb4141preproject.questions.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import seb4141preproject.questions.dto.QuestionVoteRequestDto;
import seb4141preproject.questions.dto.QuestionVoteResponseDto;
import seb4141preproject.questions.entity.Question;
import seb4141preproject.questions.entity.QuestionVote;

@Mapper(componentModel = "spring")
public interface QuestionVoteMapper {

    default QuestionVote questionVoteRequestDtoToQuestionVote(QuestionVoteRequestDto requestDto, long questionId) {
        QuestionVote questionVote = new QuestionVote();
        Question question = new Question();
        question.setId(questionId);
        questionVote.setQuestion(question);
        questionVote.setVoteStatus(requestDto.getVoteStatus());

        return questionVote;
    }

    @Mapping(target = "questionId", source = "question.id")
    @Mapping(target = "memberId", source = "member.id")
    QuestionVoteResponseDto questionVoteToQuestionVoteResponseDto(QuestionVote questionVote);
}
