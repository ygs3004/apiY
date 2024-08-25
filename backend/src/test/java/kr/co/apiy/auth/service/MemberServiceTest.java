package kr.co.apiy.auth.service;

import kr.co.apiy.auth.dto.SignupRequest;
import kr.co.apiy.auth.entity.Member;
import kr.co.apiy.auth.member.MemberService;
import kr.co.apiy.auth.member.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 테스트")
    @Transactional
    public void signupTest() {
        SignupRequest testDTO = SignupRequest.builder()
                .email("ygs3004@naver1123.com")
                .name("윤건수")
                .password("123456")
                .build();
        Member member = memberService.signup(testDTO);

        Optional<Member> savedMember = memberRepository.findById(member.getId());
        savedMember.ifPresentOrElse(
                saved -> {
                    System.out.println("saved: " + saved);
                    Assertions.assertEquals(saved.getEmail(), member.getEmail(), "가입 요청한 이메일과 다른이메일로 가입되었습니다.");
                    Assertions.assertEquals(saved.getPassword(), member.getPassword(), "가입 요청한 비밀번호와 다른 비밀번호로 가입되었습니다.");
                    Assertions.assertEquals(saved.getName(), member.getName(), "가입 요청한 이름과 다른 이름으로 가입되었습니다.");
                },
                () -> {
                    Assertions.fail("회원 가입 Process 확인 필요.");
                }
        );

    }

}
