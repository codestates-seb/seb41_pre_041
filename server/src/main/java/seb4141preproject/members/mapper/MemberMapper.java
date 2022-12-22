package seb4141preproject.members.mapper;

import org.springframework.stereotype.Component;
import seb4141preproject.members.dto.MemberPatchDto;
import seb4141preproject.members.dto.MemberPostDto;
import seb4141preproject.members.dto.MemberResponseDto;
import seb4141preproject.members.entity.Member;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Component
public class MemberMapper {

    public Member memberPostDtoToMember(MemberPostDto memberPostDto) {
        if (memberPostDto == null) return null;

        Member member = new Member();
        member.setName(memberPostDto.getName());
        member.setEmail(memberPostDto.getEmail());
        member.setPassword(memberPostDto.getPassword());

        return member;
    }

    public Member memberPatchDtoToMember(MemberPatchDto memberPatchDto) {
        if (memberPatchDto == null) return null;

        Member member = new Member();
        member.setId(memberPatchDto.getId());
        member.setName(memberPatchDto.getName());
        member.setPassword(memberPatchDto.getPassword());

        return member;
    }

    public MemberResponseDto memberToMemberResponseDto(Member member) {
        if (member == null) return null;

        MemberResponseDto responseDto = new MemberResponseDto();

        responseDto.setId(member.getId());
        responseDto.setName(member.getName());
        responseDto.setEmail(member.getEmail());
        responseDto.setPassword(member.getPassword());
        responseDto.setRoles(member.getRoles());

        return responseDto;
    }

    public List<MemberResponseDto> membersToMemberResponses(List<Member> members) {
        if (members == null) return null;

        List<MemberResponseDto> list = new ArrayList<>(members.size());
        for (Member member : members) {
            list.add(memberToMemberResponseDto(member));
        }
        return list;
    }
}
