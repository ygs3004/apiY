package kr.co.apiy.member.service;

import kr.co.apiy.member.dto.LoginDTO;
import kr.co.apiy.member.dto.SignupDTO;
import kr.co.apiy.member.entity.Member;
import kr.co.apiy.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
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

    public Member signup(SignupDTO.Request signupRequest) {
        Member newMember = Member.builder()
                .email(signupRequest.getEmail())
                .name(signupRequest.getName())
                .password(signupRequest.getPassword())
                .build();
        return memberRepository.save(newMember);
    }

    public Member login(LoginDTO.Request loginRequest) {

        Optional<Member> result = memberRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if(result.isEmpty()){
            throw new RuntimeException("존재하지 않는 회원정보입니다.");
        }

        return result.get();
    }

}
