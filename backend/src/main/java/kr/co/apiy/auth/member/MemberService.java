package kr.co.apiy.auth.member;

import kr.co.apiy.auth.dto.SignupRequest;
import kr.co.apiy.auth.entity.MemberEntity;
import kr.co.apiy.auth.entity.MemberRole;
import kr.co.apiy.auth.exception.SignupFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public MemberEntity signup(SignupRequest signupRequest) {
        Optional<MemberEntity> existMember = memberRepository.findByEmail(signupRequest.getEmail());
        existMember.ifPresent(memberEntity ->{
            throw new SignupFailException("이미 존재하는 회원입니다.");
        });

        MemberEntity newMemberEntity = MemberEntity.builder()
                .email(signupRequest.getEmail())
                .name(signupRequest.getName())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build();
        newMemberEntity.addMemberRole(MemberRole.USER);
        return memberRepository.save(newMemberEntity);
    }

}
