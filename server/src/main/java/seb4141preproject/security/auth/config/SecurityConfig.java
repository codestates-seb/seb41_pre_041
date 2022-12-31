package seb4141preproject.security.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import seb4141preproject.security.auth.handler.CustomAccessDeniedHandler;
import seb4141preproject.security.auth.handler.CustomAuthenticationEntryPoint;
import seb4141preproject.security.auth.provider.JwtTokenizer;
import seb4141preproject.security.auth.service.AuthService;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenizer jwtTokenizer;
    private final AuthService authService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
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

                .apply(new CustomFilterConfiguration(jwtTokenizer, authService))
                .and()

                .authorizeRequests(auth -> auth
                        .antMatchers("/h2/**").permitAll() // h2 데이터베이스 확인 가능하게
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // preflight 요청 모두 pass
                        .antMatchers(HttpMethod.POST, "/api/questions/{questionId}/votes").hasRole("USER") // 질문 투표 작성
                        .antMatchers("/api/questions/{questionId}/votes/me").hasRole("USER") // 질문 투표 확인, 수정
                        .antMatchers(HttpMethod.POST, "/api/questions").hasRole("USER") // 질문 작성
                        .antMatchers(HttpMethod.PATCH, "/api/questions/{questionId}")
                        .access("@questionService.checkMember(principal, T(Long).parseLong(#questionId))") // 질문 수정
                        .antMatchers(HttpMethod.DELETE, "/api/questions/{questionId}")
                        .access("@questionService.checkMember(principal, T(Long).parseLong(#questionId))") // 질문 삭제
                        .antMatchers(HttpMethod.POST, "/api/answers/{answerId}/votes").hasRole("USER") // 질문 투표 작성
                        .antMatchers("/api/answers/{answerId}/votes/me").hasRole("USER") // 답변 투표 확인, 수정
                        .antMatchers(HttpMethod.POST, "/api/answers").hasRole("USER") // 답변 작성
                        .antMatchers(HttpMethod.PATCH, "/api/answers/{answerId}")
                        .access("@answerService.checkMember(principal, T(Long).parseLong(#answerId))") // 답변 수정
                        .antMatchers(HttpMethod.DELETE, "/api/answers/{answerId}")
                        .access("@answerService.checkMember(principal, T(Long).parseLong(#answerId))") // 답변 삭제
                        .antMatchers("/api/auths/logout").hasRole("USER") // 로그아웃
                        .antMatchers("/api/members/{memberId}")
                        .access("T(seb4141preproject.members.entity.Member).cast(principal).getId() " +
                                "== T(Long).parseLong(#memberId)")// 마이페이지 확인, 회원정보 수정
                        .anyRequest().permitAll())
                .logout()
                .disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:[*]"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization", "refreshToken"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
