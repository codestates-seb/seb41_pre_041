package seb4141preproject.answers.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import seb4141preproject.answers.dto.AnswerVoteRequestDto;
import seb4141preproject.answers.dto.AnswerVoteResponseDto;
import seb4141preproject.answers.entity.Answer;
import seb4141preproject.answers.entity.AnswerVote;

@Mapper(componentModel = "spring")
public interface AnswerVoteMapper {
    default AnswerVote answerVoteRequestDtoToAnswerVote(AnswerVoteRequestDto requestDto, long answerId) {
        AnswerVote answerVote = new AnswerVote();
        Answer answer = new Answer();
        answer.setId(answerId);
        answerVote.setAnswer(answer);
        answerVote.setVoteStatus(requestDto.getVoteStatus());

        return answerVote;
    }

    @Mapping(target = "answerId", source = "answer.id")
    @Mapping(target = "memberId", source = "member.id")
    AnswerVoteResponseDto answerVoteToAnswerVoteResponseDto(AnswerVote answerVote);
}
