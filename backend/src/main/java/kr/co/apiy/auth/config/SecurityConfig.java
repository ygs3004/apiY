package kr.co.apiy.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.apiy.auth.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
// @EnableMethodSecurity(securedEnabled = true), // @Secured("ROLE_ADMIN" 형태로 컨트롤러에서 쓸때)
@EnableWebSecurity // FilterChain 동작
@Log4j2
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final ObjectMapper objectMapper;
    @Value("${jwt.secret}") String secretKey;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        httpSecurity.authenticationManager(authenticationManager);

        httpSecurity.authorizeHttpRequests( authorizeRequestConfig -> {
            authorizeRequestConfig.anyRequest().permitAll();

            // 권한별
            // authorizeRequests.requestMatchers("/**").hasRole("USER");
        });


        // 로그아웃 처리
        httpSecurity.logout(logoutConfigurer -> {
            logoutConfigurer.deleteCookies("remove")
                            .invalidateHttpSession(false);
        });

        // 세션마다 생성되는 토큰값 disabled
        // 외부에서의 API 요청을 인정할때는 disabled
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // httpSecurity.oauth2Login(oAuth2LoginConfigurer -> {
        //     oAuth2LoginConfigurer.successHandler(successHandler());
        // });

        // httpSecurity.rememberMe(rememberMeConfigurer -> {
        //     rememberMeConfigurer.tokenValiditySeconds(60 * 60 * 24 * 7);
        //     rememberMeConfigurer.userDetailsService(memberService);
        // });

        // httpSecurity.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(apiLoginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AccessTokenCheckFilter apiCheckFilter() {
        // return new AccessTokenCheckFilter("/auth/**/*", jwtUtil());
        return new AccessTokenCheckFilter("/sample/security", jwtUtil());
    }

    public LoginFilter apiLoginFilter(AuthenticationManager authenticationManager) {
        LoginFilter loginFilter = new LoginFilter("/member/login", jwtUtil(), objectMapper);
        loginFilter.setAuthenticationManager(authenticationManager);
        return loginFilter;
    }

    @Bean
    public JwtUtils jwtUtil(){
        return new JwtUtils(secretKey);
    }

}