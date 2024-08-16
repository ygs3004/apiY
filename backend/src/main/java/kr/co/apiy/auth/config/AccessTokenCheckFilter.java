package kr.co.apiy.auth.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.apiy.auth.utils.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class AccessTokenCheckFilter extends OncePerRequestFilter {

    private final AntPathMatcher antPathMatcher;
    private final String pattern;
    private final JwtUtils jwtUtils;

    public AccessTokenCheckFilter(String pattern, JwtUtils jwtUtils) {
        this.antPathMatcher = new AntPathMatcher();
        this.pattern = pattern;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("REQUEST URI: " + request.getRequestURI());
        log.info("Need Auth: " + antPathMatcher.match(pattern, request.getRequestURI()));

        if (antPathMatcher.match(pattern, request.getRequestURI())) {
            log.info("Access Token Check..............................................");

            boolean checkHeader = checkAuthHeader(request);
            if (checkHeader) {
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                String message = "FAIL CHECK API TOKEN";
                json.put("code", HttpServletResponse.SC_FORBIDDEN);
                json.put("message", message);

                PrintWriter out = response.getWriter();
                out.print(json);
            }
            return;
        }

        filterChain.doFilter(request, response);

    }

    private boolean checkAuthHeader(HttpServletRequest request) {

        boolean checkResult = false;

        String authHeader = request.getHeader("Authorization");
        String tokenPrefix = JwtUtils.TOKEN_TYPE_BEARER + " ";
        log.info("checkAuthHeader: " + authHeader);
        int tokenStartIndex = tokenPrefix.length();

        if (StringUtils.hasText(authHeader) && authHeader.startsWith(tokenPrefix)) {
            log.info("Authorization exist: " + authHeader);

            String email = jwtUtils.parseEmail(authHeader.substring(tokenStartIndex));
            log.info("Validate Email: " + email);
            checkResult = email.length() > 0;
        }

        return checkResult;
    }

}
