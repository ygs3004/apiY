package kr.co.apiy.member.controller;

import kr.co.apiy.member.dto.LoginDTO;
import kr.co.apiy.member.dto.SignupDTO;
import kr.co.apiy.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> signup(SignupDTO.Request singupRequest) {
        memberService.signup(singupRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> login(LoginDTO.Request LoginRequest) {
        memberService.login(LoginRequest);
        return ResponseEntity.ok().build();
    }

}
