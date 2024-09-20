package kr.co.apiy.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kr.co.apiy.auth.dto.JwtParseResult;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Log4j2
public class JwtUtils {

    final SecretKey key;
    public static final String TOKEN_TYPE_BEARER = "Bearer";

    public JwtUtils(String secretKey) {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createJwt(String userEmail) {
        int EXPIRED_PERIOD = (1000 * 60 * 60 * 24);
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

    public JwtParseResult jwtParse(String token){
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return JwtParseResult
                .builder()
                .email(claims.getSubject())
                .expiration(claims.getExpiration())
                .build();
    }

}
