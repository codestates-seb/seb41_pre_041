package seb4141preproject.answer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import seb4141preproject.answer.dto.AnswerDto;
import seb4141preproject.answer.entity.Answer;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {
    Answer answerPostToAnswer(AnswerDto.Post requestBody);
    Answer answerPatchToAnswer(AnswerDto.Patch requestBody);
    AnswerDto.Response answerToAnswerResponse(Answer answer);
    List<AnswerDto.Response> answersToAnswerResponses(List<Answer> answers);
}
