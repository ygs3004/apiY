package kr.co.apiy.member.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.apiy.member.dto.AuthMemberDto;
import kr.co.apiy.member.exception.LoginFailException;
import kr.co.apiy.member.utils.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Log4j2
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtUtils jwtUtils;

    public LoginFilter(String defaultFilterProcessesUrl, JwtUtils jwtUtils) {
        super(defaultFilterProcessesUrl);
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("Login Filter..............................................");
        log.info("=================== attemptAuthentication ===================");

        String email = request.getParameter("email");
        String pw = request.getParameter("password");

        try{
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, pw);
            return getAuthenticationManager().authenticate(authToken);
        }catch (AuthenticationException e){
            log.info(e.getMessage());
            throw new LoginFailException("아이디 또는 비밀번호가 잘못 되었습니다.");
        }

    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) {
        try (PrintWriter out = response.getWriter()) {
            String email = ((AuthMemberDto) authResult.getPrincipal()).getEmail();
            String token = jwtUtils.createJwt(email);
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            JSONObject json = new JSONObject();
            json.put("tokenType", JwtUtils.TOKEN_TYPE_BEARER);
            json.put("accessToken", token);
            out.print(json);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) {
        log.info("Login Fail Handler............................");
        log.info(exception.getMessage());

        try (PrintWriter out = response.getWriter()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            JSONObject json = new JSONObject();
            String message = exception.getMessage();
            json.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            json.put("message", message);
            out.print(json);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
