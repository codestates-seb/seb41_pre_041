package seb4141preproject.security.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import seb4141preproject.security.auth.dto.LoginDto;
import seb4141preproject.security.auth.dto.TokenRequestDto;
import seb4141preproject.security.auth.service.AuthService;
import seb4141preproject.testMember.entity.Member;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // 회원가입 -> MemberService에서?

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseEntity(authService.reissue(tokenRequestDto), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody TokenRequestDto tokenRequestDto,
                                 @AuthenticationPrincipal Member member) {
        if (member != null) {
            authService.logout(tokenRequestDto);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
