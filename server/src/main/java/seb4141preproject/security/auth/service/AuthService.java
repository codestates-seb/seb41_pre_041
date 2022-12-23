package seb4141preproject.security.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import seb4141preproject.members.mapper.MemberMapper;
import seb4141preproject.members.repository.MemberRepository;
import seb4141preproject.security.auth.provider.JwtTokenizer;
import seb4141preproject.security.auth.dto.LoginDto;
import seb4141preproject.security.auth.dto.TokenDto;
import seb4141preproject.security.auth.dto.TokenRequestDto;
import seb4141preproject.security.auth.entity.RefreshToken;
import seb4141preproject.security.auth.repository.RefreshTokenRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberMapper mapper;

    public TokenDto login(LoginDto loginDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
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

    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!jwtTokenizer.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 인증정보 가져오기
        Authentication authentication = jwtTokenizer.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 User ID(email) 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken =
                refreshTokenRepository.findByKey(authentication.getName())
                        .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. 유저의 Refresh Token 과 저장된 Refresh Token 이 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = createToken(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

    public void logout(TokenRequestDto tokenRequestDto) {
        // TODO: 사용된 Access Token 사용하지 못하게 처리

        System.out.println(tokenRequestDto.getRefreshToken());

        // Refresh token 제거
        RefreshToken refreshToken = refreshTokenRepository.findByValue(tokenRequestDto.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("REFRESH_TOKEN_NOT_FOUND"));

        refreshTokenRepository.delete(refreshToken);
    }

    // 클래스 내부에서만 사용 가능한 토큰 생성하는 로직
    private TokenDto createToken(Authentication authentication) {
        String accessToken = jwtTokenizer.generateAccessToken(authentication);
        String refreshToken = jwtTokenizer.generateRefreshToken(authentication);
        TokenDto tokenDto = new TokenDto(accessToken, refreshToken);
        return tokenDto;
    }
}
