package kr.co.apiy.controller;

import kr.co.apiy.dto.LoginDTO;
import kr.co.apiy.dto.SignupDTO;
import kr.co.apiy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signup(SignupDTO signupDto) {
        memberService.signup(signupDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(LoginDTO loginDTO) {
        memberService.login(loginDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
