package seb4141preproject.question.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import seb4141preproject.question.dto.QuestionVoteRequestDto;
import seb4141preproject.question.dto.QuestionVoteResponseDto;
import seb4141preproject.question.entity.QuestionVote;

@Mapper(componentModel = "spring")
public interface QuestionVoteMapper {
    QuestionVote questionVoteRequestDtoToQuestionVote(QuestionVoteRequestDto requestDto);

    @Mapping(target = "questionId", source = "question.id")
    @Mapping(target = "memberId", source = "member.id")
    QuestionVoteResponseDto questionVoteToQuestionVoteResponseDto(QuestionVote questionVote);
}
