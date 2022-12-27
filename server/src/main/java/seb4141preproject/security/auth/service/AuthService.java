package seb4141preproject.security.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import seb4141preproject.security.auth.provider.*;
import seb4141preproject.security.auth.dto.*;
import seb4141preproject.security.auth.entity.*;
import seb4141preproject.security.auth.repository.RefreshTokenRepository;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserDetailsService userDetailsService;
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenDto login(LoginDto loginDto) {
        // 1. loginDto 기반 authenticationToken 생성 (toAuthentication 메소드 활용)
        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();

        // 2. token 검증
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = createToken(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }

    public TokenDto reissue(TokenRequestDto requestDto, HttpServletRequest request) {

        // 1. Refresh Token 검증
        String accessToken = requestDto.getAccessToken();
        String refreshToken = request.getHeader("Cookie").substring(14);
        System.out.println("refreshToken : " + refreshToken);

        if (!jwtTokenizer.validateToken(refreshToken, request)) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 인증정보 가져오기
        Authentication authentication = jwtTokenizer.getAuthentication(accessToken);

        // 3. 저장소에서 User ID(email) 를 기반으로 Refresh Token 값 가져옴
        RefreshToken findRefreshToken =
                refreshTokenRepository.findByKey(authentication.getName())
                        .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. 유저의 Refresh Token 과 저장된 Refresh Token 이 일치하는지 검사
        if (!findRefreshToken.getValue().equals(refreshToken)) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = createToken(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = findRefreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);
        System.out.println("new RT : " + tokenDto.getRefreshToken());
        System.out.println("new AT : " + tokenDto.getAccessToken());

        // 토큰 발급
        return tokenDto;
    }

    public void logout(HttpServletRequest request) {

        String refreshToken = request.getHeader("Cookie").substring(14);
        System.out.println("refresh Token : " + refreshToken);
        // TODO: 사용된 토큰은 폐기 -> accessToken 의 expiration 을 짧게 설정

        // Refresh token 제거
        RefreshToken findRefreshToken = refreshTokenRepository.findByValue(refreshToken)
                .orElseThrow(() -> new RuntimeException("REFRESH_TOKEN_NOT_FOUND"));

        refreshTokenRepository.delete(findRefreshToken);
    }

    // refresh token Cookie 생성 로직
    public ResponseCookie createCookie(TokenDto tokenDto) {
        ResponseCookie cookie = ResponseCookie.from("refresh-token", tokenDto.getRefreshToken())
                        .path("/")
                        .sameSite("Lax")
                        .httpOnly(true)
                        .build();
        return cookie;
    }

    // 클래스 내부에서만 사용 가능한 토큰 생성하는 로직
    private TokenDto createToken(Authentication authentication) {
        String accessToken = jwtTokenizer.generateAccessToken(authentication);
        String refreshToken = jwtTokenizer.generateRefreshToken(authentication);
        TokenDto tokenDto = new TokenDto(accessToken, refreshToken);
        return tokenDto;
    }
}
