package seb4141preproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import seb4141preproject.members.entity.Member;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {
    @Bean
    public AuditorAware<Member> auditorProvider() {
        return () -> {
            // Spring Security에서 인증된 principal을 받아와야 한다.
            // 인증 구현 전까지는 mock 사용
            Member member = new Member();
            member.setId(1L);

            return Optional.of(member);
        };
    }
}
