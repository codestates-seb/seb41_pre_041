package seb4141preproject.answers.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import seb4141preproject.answers.dto.AnswerDto;
import seb4141preproject.answers.entity.Answer;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    Answer answerPostToAnswer(AnswerDto.Post requestBody);
    Answer answerPatchToAnswer(AnswerDto.Patch requestBody);
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "questionId", source = "question.id")
    @Mapping(target = "memberName", source = "member.name")
    AnswerDto.Response answerToAnswerResponse(Answer answer);
    List<AnswerDto.Response> answersToAnswerResponses(List<Answer> answers);
}