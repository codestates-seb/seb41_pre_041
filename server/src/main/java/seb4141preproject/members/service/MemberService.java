package seb4141preproject.members.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seb4141preproject.members.entity.*;
import seb4141preproject.members.repository.*;
import seb4141preproject.security.auth.redis.RedisDao;
import seb4141preproject.security.auth.utils.CustomAuthorityUtils;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    private final RedisDao redisDao;

    public Member createMember(Member member) {

        // 기존 이메일 존재 / 회원 생성 실패
        verifyExistsEmail(member.getEmail());
        // password 암호화
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);
        // User Role 저장
        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        Member savedMember = memberRepository.save(member);

        // DB에 회원 정보 저장
        return savedMember;
    }

    public Member updateMember(Member member) { // 매개변수 member -> patchDto를 member로 변환한 객체
        Member findMember = findVerifiedMember(member.getId()); // 실제 데이터가 있는지 검증

        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> findMember.setPassword(passwordEncoder.encode(password)));

        Member savedMember = memberRepository.save(findMember);

        return savedMember;
    }
   public Member findMember(long id) {
       return findVerifiedMember(id);
   }
   public void deleteMember (long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        Member member = optionalMember.orElseThrow(() ->
                new RuntimeException("존재하지 않는 회원입니다."));

        redisDao.deleteValues(member.getEmail()); // 해당 회원의 refreshToken 을 redis 에서 삭제

        memberRepository.deleteById(id);
   }

   private void verifyExistsEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            throw new RuntimeException("이미 가입된 회원입니다.");
        }
   }

   private Member findVerifiedMember(long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        Member findMember = optionalMember.orElseThrow(() ->
                new RuntimeException("MEMBER NOT FOUND"));

        return findMember;
   }
}
