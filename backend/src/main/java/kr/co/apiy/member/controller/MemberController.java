package kr.co.apiy.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.apiy.member.dto.LoginRequest;
import kr.co.apiy.member.dto.SignupRequest;
import kr.co.apiy.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인/회원가입", description = "로그인/회원가입 API")
@RestController
@RequestMapping("/member/*")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> signup(@Valid SignupRequest signupRequest) {
        memberService.signup(signupRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(LoginRequest LoginRequest) {
        /* Spring Security Filter 에서 처리, Controller 진입전에 처리완료 */
    }

}