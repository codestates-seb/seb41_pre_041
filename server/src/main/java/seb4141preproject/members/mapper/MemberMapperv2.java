package seb4141preproject.members.mapper;


import seb4141preproject.members.dto.MemberPatchDto;
import seb4141preproject.members.dto.MemberPostDto;
import seb4141preproject.members.dto.MemberResponseDto;
import seb4141preproject.members.entity.Member;

import java.util.List;

public interface MemberMapperv2 {
    Member memberPostToMember(MemberPostDto requestBody);
    Member memberPatchToMember(MemberPatchDto requestBody);
    MemberResponseDto memberToMemberResponse(Member member);
    List<MemberResponseDto> membersToMemberResponses(List<Member> members);
}