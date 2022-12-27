package seb4141preproject.questions.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import seb4141preproject.questions.dto.QuestionRequestDto;
import seb4141preproject.questions.dto.QuestionResponseDto;
import seb4141preproject.questions.entity.Question;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question questionRequestDtoToQuestion(QuestionRequestDto requestDto);

    Question questionRequestDtoToQuestion(QuestionRequestDto requestDto, long id);

    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "memberName", source = "member.name")
    QuestionResponseDto questionToQuestionResponseDto(Question question);

    List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions);
}
