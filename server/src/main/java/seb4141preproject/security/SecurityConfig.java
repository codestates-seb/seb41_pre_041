package seb4141preproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;
import seb4141preproject.security.auth.JwtTokenizer;
import seb4141preproject.security.auth.filter.JwtAuthenticationFilter;
import seb4141preproject.security.auth.handler.AuthFailureHandler;
import seb4141preproject.security.auth.handler.AuthSuccessHandler;

@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenizer jwtTokenizer;

    public SecurityConfig(JwtTokenizer jwtTokenizer) {
        this.jwtTokenizer = jwtTokenizer;
    }

    @Bean
    @CrossOrigin // TODO : 구체적인 CORS 설정 필요
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeRequests(auth -> auth // TODO : 회원, 비회원 권한 조정 필요, Jwt Authentication 필요
                        .anyRequest().permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            jwtAuthenticationFilter.setFilterProcessesUrl("/members/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new AuthSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new AuthFailureHandler());

            builder.addFilter(jwtAuthenticationFilter);
        }
    }
}
