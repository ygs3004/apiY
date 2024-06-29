package kr.co.apiy.service;

import kr.co.apiy.dto.LoginDTO;
import kr.co.apiy.dto.SignupDTO;
import kr.co.apiy.entity.Member;
import kr.co.apiy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signup(SignupDTO signupDTO){
        try{
            Member newMember = Member.builder()
                    .email(signupDTO.getEmail())
                    .name(signupDTO.getName())
                    .password(signupDTO.getPassword())
                    .build();
            return memberRepository.save(newMember);
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
            if(e.getMessage().contains("not-null")){
                throw new RuntimeException("필수 입력 데이터가 없습니다.");
            }
            throw new RuntimeException("올바르지 않은 데이터입니다.");
        }
    }

    public Member login(LoginDTO loginDTO) {

        Optional<Member> result = memberRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if(result.isEmpty()){
            throw new RuntimeException("존재하지 않는 회원정보입니다.");
        }

        return result.get();
    }

}
