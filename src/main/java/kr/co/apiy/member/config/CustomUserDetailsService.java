package kr.co.apiy.member.config;

import kr.co.apiy.member.dto.AuthMemberDto;
import kr.co.apiy.member.entity.Member;
import kr.co.apiy.member.exception.LoginFailException;
import kr.co.apiy.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService{

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Member> result = memberRepository.findByEmail(email);

        if (result.isPresent()) {
            Member member = result.get();
            return new AuthMemberDto(
                    member.getEmail(),
                    member.getPassword(),
                    member.getRoleSet().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                            .collect(Collectors.toList()));

        } else {
            throw new LoginFailException("존재하지 않는 유저");
        }

    }
}