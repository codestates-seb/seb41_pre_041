package seb4141preproject.members.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seb4141preproject.members.entity.Member;
import seb4141preproject.members.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(Member member) throws IllegalAccessException {

        // 기존 이메일 존재 / 회원 생성 실패
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null)
            throw new IllegalAccessException("이미 가입된 회원입니다.");

        member.setRoles(List.of("ROLE_USER"));
        // DB에 회원 정보 저장
        return memberRepository.save(member);
    }

    public Member updateMember(Member member) { // 매개변수 member -> patchDto를 member로 변환한 객체
        Member findMember = findVerifiedMember(member.getId()); // 실제 데이터가 있는지 검증

        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> findMember.setPassword(password)); // TODO : passwordEncoder 적용 필요

        Member savedMember = memberRepository.save(findMember);

        return savedMember;
    }
   public Member findMember(long id) {
       Optional<Member> optional = memberRepository.findById(id);

       return optional.get();
   }
   public void deleteMember (long id) {
        memberRepository.deleteById(id);
   }

    private Member findVerifiedMember(long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        Member findMember = optionalMember.orElseThrow(() ->
                new RuntimeException("MEMBER NOT FOUND"));

        return findMember;
    }
}
