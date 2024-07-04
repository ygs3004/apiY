package kr.co.apiy.utils;

import kr.co.apiy.member.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
public class JwtTest {

    @Autowired
    JwtUtils jwtUtils;

    @Test
    public void jwtCreateTest() {
        String token = jwtUtils.createJwt(new HashMap<>());
        System.out.println(token);
    }

}
