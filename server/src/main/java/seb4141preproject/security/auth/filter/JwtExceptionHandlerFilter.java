package seb4141preproject.security.auth.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import seb4141preproject.security.auth.dto.TokenDto;
import seb4141preproject.security.auth.provider.JwtTokenizer;
import seb4141preproject.security.auth.service.AuthService;
import seb4141preproject.security.auth.utils.ErrorResponder;
import seb4141preproject.security.auth.utils.HeaderMapRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;
    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        HeaderMapRequestWrapper wrapper = new HeaderMapRequestWrapper(request); // (reissue 시) header 값 덮어쓰기를 위한 wrapper 객체 생성

        try {
            filterChain.doFilter(request, response);

            log.info("Exception Filter filterChain 실행");

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("잘못된 Access Token 서명입니다.", e);
            ErrorResponder.sendJwtErrorResponse(response, e.getMessage());

        } catch (UnsupportedJwtException e) {
            ErrorResponder.sendJwtErrorResponse(response, e.getMessage());
            log.error("지원되지 않는 Access Token 입니다.", e);

        } catch (IllegalArgumentException e) {
            ErrorResponder.sendJwtErrorResponse(response, e.getMessage());
            log.error("Access Token 이 잘못되었습니다.", e);

        } catch (ExpiredJwtException e) {
            log.error("만료된 Access Token 입니다. 재발급을 진행합니다.");

            String refreshToken = resolveRefreshToken(request); // request header 에서 refreshToken 추출

            Authentication authentication = jwtTokenizer.getAuthentication(refreshToken); // refreshToken 으로 authentication 생성
            // -> 만료된 accessToken 으로는 getAuthentication 불가능하기 때문
            SecurityContextHolder.getContext().setAuthentication(authentication);

            TokenDto tokenDto = authService.reissue(request, authentication); // 토큰 재발급
            setHeaderResponse(wrapper, response, tokenDto); // header, response 값 재설정

            log.error("Access Token, Refresh Token 재발급이 완료되었습니다.");

            filterChain.doFilter(wrapper, response);
        }
    }

    private String resolveRefreshToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("RefreshToken");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    private void setHeaderResponse(HeaderMapRequestWrapper wrapper,
                                   HttpServletResponse response,
                                   TokenDto tokenDto) {
        // accessToken, refreshToken Header 값 새로 덮어쓰기
        wrapper.addHeader("Authorization", tokenDto.getAccessToken());
        wrapper.addHeader("RefreshToken", tokenDto.getRefreshToken());

        // reissue 후 response header 에 새로운 accessToken, refreshToken 값 추가
        response.addHeader("Authorization", tokenDto.getAccessToken());
        response.addHeader("RefreshToken", tokenDto.getRefreshToken());
    }
}
