package seb4141preproject.security.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;
import seb4141preproject.security.auth.provider.*;
import seb4141preproject.security.auth.handler.*;
import seb4141preproject.security.auth.utils.*;

@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;

    public SecurityConfig(JwtTokenizer jwtTokenizer, CustomAuthorityUtils authorityUtils) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
    }

    @Bean
    @CrossOrigin // TODO : 구체적인 CORS 설정 필요
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable() // h2 데이터베이스 확인 가능하게
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
                        .antMatchers(HttpMethod.POST, "/api/questions").hasRole("USER") // 질문 작성
                        .antMatchers(HttpMethod.PATCH, "/api/questions/{question-id}").hasRole("USER") // 질문 수정
                        .antMatchers(HttpMethod.DELETE, "/api/questions/{question-id}").hasRole("USER") // 질문 삭제
                        .antMatchers(HttpMethod.POST, "/api/answers").hasRole("USER") // 답변 작성
                        .antMatchers(HttpMethod.PATCH, "/api/answers/{answer-id}").hasRole("USER") // 답변 수정
                        .antMatchers(HttpMethod.DELETE, "/api/answers/{answer-id}").hasRole("USER") // 답변 삭제
                        .antMatchers("/api/auths/reissue").hasRole("USER") // 토큰 재발급
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
