package seb4141preproject.security.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import seb4141preproject.security.auth.provider.*;
import seb4141preproject.security.auth.filter.*;
import seb4141preproject.security.auth.service.AuthService;

@RequiredArgsConstructor
public class CustomFilterConfiguration extends AbstractHttpConfigurer<CustomFilterConfiguration, HttpSecurity>{
    private final JwtTokenizer jwtTokenizer;
    private final AuthService authService;

    @Override
        public void configure(HttpSecurity http) { // Custom Filter 추가 (jwtTokenizer 주입)
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenizer);
            JwtExceptionHandlerFilter jwtExceptionHandlerFilter = new JwtExceptionHandlerFilter(jwtTokenizer, authService);

            http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(jwtExceptionHandlerFilter, JwtAuthenticationFilter.class);
        }
}
