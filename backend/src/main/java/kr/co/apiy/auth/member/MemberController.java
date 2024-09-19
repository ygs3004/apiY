package kr.co.apiy.auth.member;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.co.apiy.auth.dto.LoginRequest;
import kr.co.apiy.auth.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인/회원가입", description = "로그인/회원가입 API")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> signup(@Valid @RequestBody SignupRequest signupRequest) {
        memberService.signup(signupRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse( responseCode = "401", description = "실패")
    })
    public void login(LoginRequest LoginRequest) {
        /* Spring Security Filter 에서 처리, Controller 진입전에 처리완료 */
    }

}
