package kr.co.apiy.member.service;

import kr.co.apiy.member.dto.LoginRequest;
import kr.co.apiy.member.dto.SignupRequest;
import kr.co.apiy.member.entity.Member;
import kr.co.apiy.member.entity.MemberRole;
import kr.co.apiy.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public Member signup(SignupRequest signupRequest) {
        Member newMember = Member.builder()
                .email(signupRequest.getEmail())
                .name(signupRequest.getName())
                .password(signupRequest.getPassword())
                .build();
        newMember.addMemberRole(MemberRole.USER);
        return memberRepository.save(newMember);
    }

    public Member login(LoginRequest loginRequest) {

        Optional<Member> result = memberRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if(result.isEmpty()){
            throw new RuntimeException("존재하지 않는 회원정보입니다.");
        }

        return result.get();
    }

}
