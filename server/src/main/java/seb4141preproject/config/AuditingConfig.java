package seb4141preproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import seb4141preproject.members.entity.Member;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {
    @Bean
    public AuditorAware<Member> auditorProvider() {
        // 메소드 실행 단계마다 null이 반환될 수 있어서 NullPointerException을 피하기 위해 map, filter 사용
        // 중간 과정에 문제가 있었던 경우 Optional.empty()가 반환된다.
        return () ->
                Optional.ofNullable(SecurityContextHolder.getContext())
                        .map(SecurityContext::getAuthentication)
                        .filter(Authentication::isAuthenticated)
                        .map(Authentication::getPrincipal)
                        .map(Member.class::cast);
    }
}
