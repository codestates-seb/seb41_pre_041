package seb4141preproject.members.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import seb4141preproject.members.dto.MemberLoginDto;
import seb4141preproject.members.dto.MemberPatchDto;
import seb4141preproject.members.dto.MemberPostDto;
import seb4141preproject.members.dto.MemberResponseDto;
import seb4141preproject.members.entity.Member;
import seb4141preproject.members.mapper.MemberMapperv2;
import seb4141preproject.members.service.MemberService;


import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final MemberMapperv2 mapper;

    private SecurityService securityService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    // 스프링 시큐리티에 사용 대경님한테 물어볼것


    @Autowired // 스프링 DI(Dependency Injection)에서 사용되는 어노테이션 즉 의존성 주입 어노테이션
    public  MemberController(MemberService memberService, MemberMapperv2 mapper,
                             SecurityService securityService, BCryptPasswordEncoder
                             bCryptPasswordEncoder){
        this.memberService = memberService;
        this.mapper = mapper;
        this.securityService = securityService;
        this.bCryptPasswordEncoder =bCryptPasswordEncoder;
    }



    // 회원가입
    @ApiOperation(value="회원 가입", notes = "회원-닉네임, 회원-이메일, 회원-비밀번호를 입력하여 회원가입을 합니다.")
    @PostMapping("/signup")
// 요청 URL에 매핑된 API에 대한 설명
    public ResponseEntity postMember(@RequestBody @Valid MemberPostDto memberPostDto) throws IllegalAccessException {
        Member member = mapper.memberPostToMember(memberPostDto);

        Member response = memberService.createMember(member);
        return new ResponseEntity(mapper.memberToMemberResponseDto(response),
               HttpStatus.CREATED);
    }

// 로그인
    @PostMapping("/login")
    @ApiOperation(value="로그인", notes = "회원-이메일과 회원-비밀번호를 입력해서 로그인")
    public ResponseEntity login(@RequestBody @Valid MemberLoginDto memberLoginDto){
        Member findMember = memberService.findMember(memberLoginDto.getEmail());

        // raw 패스워드와 암호화 된 password 비교가능한 메서드
        if (!bCryptPasswordEncoder.matches(memberLoginDto.getPassword(), findMember.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String token = securityService.createToken(String.valueOf(findMember.getId()),(5*1000*60));
        MemberResponseDto memberResponseDto = mapper.memberToMemberResponseDto(findMember);
        memberResponseDto.setmemberToken(token);
        return new ResponseEntity(memberResponseDto,
                HttpStatus.OK);
    }
    @ApiOperation(value = "로그아웃")
    @PutMapping("/logout")
    public  String logout(){
        return "로그아웃";
    }


// 사용자 정보 조회
     @ApiOperation(value="특정 회원 조회", notes = "회원-식별자를 이용하여 특정 회원을 조회")
     @GetMapping("/{member-id}")
     public ResponseEntity getMember(@ApiParam("회원-식별자")@PathVariable("member-id") long id){
        System.out.println("id" + id);

        Member response = memberService.findMember(id);

        return new ResponseEntity(mapper.memberToMemberResponseDto(response),
                HttpStatus.OK);

        }
// 사용자 정보 수정
     @ApiOperation(value="특정 회원 수정", notes = "회원-식별자와 수정데이터를 이용하여 특정 회원을 수정")
     @PatchMapping("/{member-id}")
     public ResponseEntity patchMember(@ApiParam("회원-식별자")@PathVariable("member-id") long id,
                                       @RequestBody MemberPatchDto memberPatchDto) {
         System.out.println("MemberController.patchMember");

         memberService.updateMember(id, memberPatchDto);
         Member response = memberService.findMember(id);

         return new ResponseEntity<>(mapper.memberToMemberResponseDto(response),
                 HttpStatus.OK);
     }
// 사용자 정보 삭제
    @ApiOperation(value="특정 회원 삭제", notes = "회원-식별자를 이용하여 특정 회원을 삭제합니다.")
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@ApiParam("회원-식별자") @PathVariable("member-id") long id) {
        System.out.println("MemberController.deleteMember");

        memberService.deleteMember(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}