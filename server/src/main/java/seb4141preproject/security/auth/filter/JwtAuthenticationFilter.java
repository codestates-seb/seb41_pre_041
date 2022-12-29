package seb4141preproject.security.auth.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import seb4141preproject.security.auth.provider.*;
import seb4141preproject.security.auth.utils.HeaderMapRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // request header 에서 accessToken 추출
        String accessToken = resolveToken(request);
        // HttpServletRequest 의 header 를 수정하기 위한 wrapper 객체 생성
        HeaderMapRequestWrapper wrapper = new HeaderMapRequestWrapper(request);

        // validateToken 이 이상없이 됐을 경우
        if (StringUtils.hasText(accessToken) && jwtTokenizer.validateToken(accessToken, request) == 0) {

            Authentication authentication = jwtTokenizer.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        // jwt 토큰이 만료되었을 경우
        } else if (StringUtils.hasText(accessToken) && jwtTokenizer.validateToken(accessToken, request) == 1) {

            // autoReissue 로직을 거쳐서 얻은 accessToken, refreshToken 을 map 으로 받음
            Map<String, String> map = autoReissue(request);

            // 새로운 accessToken 으로 authentication 생성
            Authentication authentication = jwtTokenizer.getAuthentication(map.get("authorization"));
            // SecurityContextHolder 에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // wrapper 로 감싼 HttpServletRequest header 에 새로운 accessToken, refreshToken 값 할당
            wrapper.addHeader("Authorization", "Bearer " + map.get("authorization"));
            wrapper.addHeader("RefreshToken", "Bearer " + map.get("refreshToken"));
        }
        // validateToken 결과값이 2(정상통과 or 토큰 만료가 아닌 겅우)인 경우 바로 다음 필터링 진행
        filterChain.doFilter(wrapper, response);
    }

    @Override
    // 해당 url 들은 filter 를 거치지 않도록 설정 (토큰 인증이 필요 없는 endpoint)
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String[] urls = new String[] {"/api/auths/reissue", "/api/members", "/api/auths/login"};

        return Arrays.stream(urls).anyMatch(s -> s.equals(path));
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Map autoReissue(HttpServletRequest request) { // restTemplate 을 이용한 http request 전송
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = new HttpHeaders(); // Header 인스턴스 생성

        headers.setContentType(MediaType.APPLICATION_JSON); // 서버가 받는 데이터 타입 : JSON
        headers.set("Authorization", request.getHeader("Authorization"));
        headers.set("refreshToken", request.getHeader("refreshToken"));

        HttpEntity<String> httpRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity("http://localhost:8080/api/auths/reissue", httpRequest, String.class); // TODO : 실제 서버 운용 시, 서버 url 작성  필요

        Map<String, String> map = new HashMap<>();
        map.put("authorization", response.getHeaders().getFirst("Authorization"));
        map.put("refreshToken", response.getHeaders().getFirst("RefreshToken"));

        return map;
    }
}
