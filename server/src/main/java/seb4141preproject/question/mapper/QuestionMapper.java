package seb4141preproject.question.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import seb4141preproject.question.dto.QuestionRequestDto;
import seb4141preproject.question.dto.QuestionResponseDto;
import seb4141preproject.question.entity.Question;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question questionRequestDtoToQuestion(QuestionRequestDto requestDto);

    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "memberName", source = "member.name")
    QuestionResponseDto questionToQuestionResponseDto(Question question);

    List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions);
}
