package seb4141preproject.security.auth.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import seb4141preproject.security.auth.provider.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String accessToken = resolveAccessToken(request); // request header 에서 accessToken 추출

        log.info("Authentication Filter validateToken 실행");

        if (jwtTokenizer.validateToken(accessToken)) {
            Authentication authentication = jwtTokenizer.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    // 해당 url 들은 filter 를 거치지 않도록 설정 (토큰 인증이 필요 없는 endpoint)
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI(); // URL
        String method = request.getMethod(); // request 의 메소드 종류
        String[] urls = new String[] {"/api/members", "/api/auths/login", "/h2.*"}; // 회원가입, 로그인, h2는 토큰이 필요 없음

        log.info("path : " + path);
        log.info("match ? : " + Arrays.stream(urls).anyMatch(s -> path.matches(s)));

        return Arrays.stream(urls).anyMatch(s -> path.matches(s))
                || (method.equals("GET") && !path.matches(".*/me|/api/members/.*"));
    }

    private String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
