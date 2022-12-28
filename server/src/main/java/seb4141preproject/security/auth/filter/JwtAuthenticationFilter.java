package seb4141preproject.security.auth.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import seb4141preproject.security.auth.provider.*;
import seb4141preproject.security.auth.utils.ModifiableHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;

    // 실제 필터링 로직
    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1. Request Header 에서 토큰 꺼냄
        String accessToken = resolveToken(request);
        ModifiableHttpServletRequest wrapper = new ModifiableHttpServletRequest(request);

        // 2. validationToken 으로 토큰 유효성 검사
        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
        if (StringUtils.hasText(accessToken) && jwtTokenizer.validateToken(accessToken, request) == 0) { // validateToken 이 이상없이 됐을 경우

            Authentication authentication = jwtTokenizer.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } else if (jwtTokenizer.validateToken(accessToken, request) == 2) { // jwt 토큰이 만료되었을 경우
            Map<String, String> map = jwtTokenizer.postReissue(request);

            System.out.println("새로운 AT : " + map.get("authorization"));
            System.out.println("새로운 RT : " + map.get("refreshToken"));

            Authentication authentication = jwtTokenizer.getAuthentication(map.get("authorization"));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("reissue 전 AT : " + request.getHeader("Authorization"));
            System.out.println("reissue 전 RT : " + request.getHeader("RefreshToken"));

//            wrapper.getParameter("Authorization").re
            wrapper.setAttribute("RefreshToken", "Bearer " + map.get("refreshToken"));

            System.out.println("reissue 후 AT : " + wrapper.getHeader("Authorization"));
            System.out.println("reissue 후 RT : " + wrapper.getHeader("RefreshToken"));

        }
        filterChain.doFilter(wrapper, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) { // reissue 로직은 filter 를 거치지 않도록 수정
        String path = request.getRequestURI();
        return "/api/auths/reissue".equals(path);
    }

    // RequestHeader 에서 토큰 정보를 꺼내오기
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
