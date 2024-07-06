package kr.co.apiy.member.service;

import kr.co.apiy.member.dto.SignupRequest;
import kr.co.apiy.member.entity.Member;
import kr.co.apiy.member.entity.MemberRole;
import kr.co.apiy.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public Member signup(SignupRequest signupRequest) {
        Member newMember = Member.builder()
                .email(signupRequest.getEmail())
                .name(signupRequest.getName())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build();
        newMember.addMemberRole(MemberRole.USER);
        return memberRepository.save(newMember);
    }

}
