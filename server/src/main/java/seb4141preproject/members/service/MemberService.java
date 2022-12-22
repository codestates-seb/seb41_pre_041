package seb4141preproject.members.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seb4141preproject.members.dto.MemberPatchDto;
import seb4141preproject.members.entity.Member;
import seb4141preproject.members.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Member createMember(Member member) throws IllegalAccessException {

        // 회원 미존재
        if (member == null || member.getEmail() ==null) {
            throw  new RuntimeException("Invalid arguments");
        }

        // 기존 이메일 존 / 회원 생성 실패
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null)
            throw new IllegalAccessException("이미 가입된 회원입니다.");

        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRoles("ROLE_USER");
        // DB에 회원 정보 저장
        return memberRepository.save(member);


    }
    public Member findMember(String email) {
        return memberRepository.findByEmail(email);
    }
    public void updateMember(long id, MemberPatchDto member) {
        Member findMember = memberRepository.findById(id);
        if (member.getName() != null)
            findMember.setName(member.getName());
        if (member.getPassword() != null)
            findMember.setPassword(member.getPassword());
    }
   public Member findMember(long id) {
       Optional<Member> optional = memberRepository.findById(id);

       return optional.get();
   }
   public void deleteMember (long id) {

   }
}
