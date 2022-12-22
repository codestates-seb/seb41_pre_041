package seb4141preproject.question;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import seb4141preproject.question.dto.*;
import seb4141preproject.question.entity.Question;
import seb4141preproject.question.entity.QuestionVote;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question questionRequestDtoToQuestion(QuestionRequestDto requestDto);

    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "memberName", source = "member.name")
    QuestionResponseDto questionToQuestionResponseDto(Question question);

    List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions);

    QuestionVote questionVoteRequestDtoToQuestionVote(QuestionVoteRequestDto requestDto);

    @Mapping(target = "questionId", source = "question.id")
    @Mapping(target = "memberId", source = "member.id")
    QuestionVoteResponseDto questionVoteToQuestionVoteResponseDto(QuestionVote questionVote);
}
