package seb4141preproject.security.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import seb4141preproject.members.entity.Member;
import seb4141preproject.security.auth.dto.*;
import seb4141preproject.security.auth.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auths")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = {"Authorization", "refreshToken"}, allowCredentials = "true")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

        TokenDto tokenDto = authService.login(loginDto);

        response.addHeader("Authorization", tokenDto.getAccessToken());
        response.addHeader("refreshToken", tokenDto.getRefreshToken());

        return new ResponseEntity<>("Login Successful!", HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity reissue(HttpServletRequest request, HttpServletResponse response) {

        TokenDto tokenDto = authService.reissue(request);

        response.addHeader("Authorization", tokenDto.getAccessToken());
        response.addHeader("refreshToken", tokenDto.getRefreshToken());

        return new ResponseEntity("Reissue Successful!", HttpStatus.OK);
    }

    @PostMapping("/logout")
        public ResponseEntity logout(HttpServletRequest request) {

        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (member != null) {
            authService.logout(request);
            return new ResponseEntity<>("Logout Successful!", HttpStatus.OK);
        } else {
            return new ResponseEntity("User not Found", HttpStatus.NOT_FOUND);
        }
    }
}
