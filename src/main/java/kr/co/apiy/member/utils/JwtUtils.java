package kr.co.apiy.member.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

public class JwtUtils {

    final Key key;

    public JwtUtils(String secretKey) {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createJwt(HashMap<String, Object> claims) {
        int EXPIRED_PERIOD = (1000 * 60 * 30);
        return Jwts.builder()
                .claim("claims", claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRED_PERIOD))
                .signWith(key)
                .compact();
    }

    // public Jws<Claims> parseClaims(String token){
    //     JwtParser parser = Jwts.parser().setSigningKey(JWT_SECRET_KEY);
    //     Jwt jwt = parser.parse(token);
    //     String tokenType = jwt.getHeader().get("type").toString();
    // }

}
