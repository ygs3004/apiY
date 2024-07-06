package kr.co.apiy.member.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtils {

    final SecretKey key;

    public JwtUtils(String secretKey) {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createJwt(String userEmail) {
        int EXPIRED_PERIOD = (1000 * 60 * 30);
        return Jwts.builder()
                .claim("sub", userEmail)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRED_PERIOD))
                .signWith(key)
                .compact();
    }

    public String parseEmail(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

}
