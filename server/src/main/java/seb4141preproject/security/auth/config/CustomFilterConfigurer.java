package seb4141preproject.security.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import seb4141preproject.security.auth.JwtTokenizer;
import seb4141preproject.security.auth.filter.JwtAuthenticationFilter;

@RequiredArgsConstructor
public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity>{
    private final JwtTokenizer jwtTokenizer;

    // JwtTokenizer 를 주입받아 JwtFilter 를 통해 Security 로직에 필터를 등록
    // 커스텀한 jwtAuthenticationFilter 를 Security Filter 앞에 추가한다.
    @Override
        public void configure(HttpSecurity http) throws Exception {
            JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtTokenizer);
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        }
}
