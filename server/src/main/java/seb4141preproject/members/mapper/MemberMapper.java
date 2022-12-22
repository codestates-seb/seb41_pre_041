package seb4141preproject.members.mapper;

import org.springframework.stereotype.Component;
import seb4141preproject.members.dto.MemberPostDto;
import seb4141preproject.members.entity.Member;

import java.sql.Array;
import java.util.ArrayList;

@Component
public class MemberMapper {

    public Member memberPostDtoToMember(MemberPostDto memberPostDto) {
        return new Member(0L,
                memberPostDto.getName(),
                memberPostDto.getEmail(),
                memberPostDto.getPassword())



    }
}
