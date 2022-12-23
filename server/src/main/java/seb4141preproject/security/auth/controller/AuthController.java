package seb4141preproject.security.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seb4141preproject.security.auth.dto.*;
import seb4141preproject.security.auth.service.*;

@RestController
@RequestMapping("/api/auths")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // 회원가입 -> MemberService에서 처리.

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
                                 @AuthenticationPrincipal User user) {
        if (user != null) authService.logout(tokenRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
