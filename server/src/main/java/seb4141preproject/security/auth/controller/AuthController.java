package seb4141preproject.security.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import seb4141preproject.security.auth.dto.LoginDto;
import seb4141preproject.security.auth.dto.TokenDto;
import seb4141preproject.security.auth.dto.TokenRequestDto;
import seb4141preproject.security.auth.service.AuthService;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/api/auths")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // 회원가입 -> MemberService에서 처리.

    @PostMapping("/login") // TODO : refresh Token 생성 후 cookie 저장?
    public ResponseEntity login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

//        Cookie setting 로직 초안
        TokenDto tokenDto = authService.login(loginDto);
//        Cookie cookie = authService.createCookie(tokenDto);
//        response.addCookie(cookie);

        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
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
