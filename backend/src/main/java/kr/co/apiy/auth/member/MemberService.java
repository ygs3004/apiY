package kr.co.apiy.auth.member;

import kr.co.apiy.auth.dto.SignupRequest;
import kr.co.apiy.auth.entity.Member;
import kr.co.apiy.auth.dto.MemberRole;
import kr.co.apiy.auth.exception.SignupFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member signup(SignupRequest signupRequest) {
        Optional<Member> existMember = memberRepository.findByEmail(signupRequest.getEmail());
        existMember.ifPresent(member ->{
            throw new SignupFailException("이미 존재하는 회원정보입니다.");
        });

        Member newMember = Member.builder()
                .email(signupRequest.getEmail())
                .name(signupRequest.getName())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build();
        newMember.addMemberRole(MemberRole.USER);
        return memberRepository.save(newMember);
    }

}
