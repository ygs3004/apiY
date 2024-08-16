package kr.co.apiy.auth.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class JwtTest {

    @Autowired
    JwtUtils jwtUtils;

    @Test
    @DisplayName("Jwt Token Parsing Test")
    public void jwtCreateTest() {
        String userEmail = "ygs3004@naver.com";
        String token = jwtUtils.createJwt(userEmail);
        String parseEmail = jwtUtils.parseEmail(token);
        Assertions.assertEquals(userEmail, parseEmail, "토큰 Parsing 이 정상적이지 않습니다.");
    }

}
