package seb4141preproject.security.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import seb4141preproject.security.auth.handler.CustomAccessDeniedHandler;
import seb4141preproject.security.auth.handler.CustomAuthenticationEntryPoint;
import seb4141preproject.security.auth.provider.JwtTokenizer;

@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenizer jwtTokenizer;

    public SecurityConfig(JwtTokenizer jwtTokenizer) {
        this.jwtTokenizer = jwtTokenizer;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .and()

                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .httpBasic().disable()
                .formLogin().disable()

                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()

                .apply(new CustomFilterConfigurer(jwtTokenizer))
                .and()

                .authorizeRequests(auth -> auth // TODO : 회원, 비회원 권한 조정 필요
                        .antMatchers("/h2/**").permitAll() // h2 데이터베이스 확인 가능하게
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // preflight 요청 모두 pass
                        .antMatchers(HttpMethod.POST, "/api/questions/{questionId}/votes").hasRole("USER") // 질문 투표 작성
                        .antMatchers(HttpMethod.PATCH, "/api/questions/{questionId}/votes/me")
                        .hasRole("USER") // 질문 투표 수정
                        .antMatchers(HttpMethod.POST, "/api/questions").hasRole("USER") // 질문 작성
                        .antMatchers(HttpMethod.PATCH, "/api/questions/{questionId}")
                        .access("@questionService.checkMember(authentication,#questionId)") // 질문 수정
                        .antMatchers(HttpMethod.DELETE, "/api/questions/{questionId}")
                        .access("@questionService.checkMember(authentication,#questionId)")// 질문 삭제
                        .antMatchers(HttpMethod.POST, "/api/answers").hasRole("USER") // 답변 작성
                        .antMatchers(HttpMethod.PATCH, "/api/answers/{answer-id}").hasRole("USER") // 답변 수정
                        .antMatchers(HttpMethod.DELETE, "/api/answers/{answer-id}").hasRole("USER") // 답변 삭제
//                        .antMatchers("/api/auths/reissue").hasRole("USER") // 토큰 재발급
                        .antMatchers("/api/auths/logout").hasRole("USER") // 로그아웃
                        .antMatchers("/api/members/{member-id}").hasRole("USER") // 마이페이지 확인, 회원정보 수정

                        .anyRequest().permitAll())
                .logout()
                .disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
