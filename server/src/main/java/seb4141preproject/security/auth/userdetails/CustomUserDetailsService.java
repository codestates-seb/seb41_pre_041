package seb4141preproject.security.auth.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import seb4141preproject.members.entity.Member;
import seb4141preproject.members.repository.MemberRepository;
import seb4141preproject.security.auth.utils.CustomAuthorityUtils;

import java.util.Collection;
import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository; // TODO : 희성님께서 만드신 memberRepository 주입 필요
    private final CustomAuthorityUtils authorityUtils;

    public CustomUserDetailsService(MemberRepository memberRepository, CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository; // TODO : 희성님께서 만드신 memberRepository 주입 필요
        this.authorityUtils = authorityUtils;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByEmail(email); // TODO : 희성님께서 만드신 memberRepository 주입 필요
        Member findMember = optionalMember.orElseThrow(() ->
                new UsernameNotFoundException("email not found"));

        return new MemberDetails(findMember);
    }

    private final class MemberDetails extends Member implements UserDetails {
        MemberDetails(Member member) {
            setId(member.getId());
            setName(member.getName());
            setEmail(member.getEmail());
            setPassword(member.getPassword());
            setRoles(member.getRoles());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRoles());
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
