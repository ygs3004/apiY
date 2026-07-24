package kr.co.apiy.auth.config;

import kr.co.apiy.auth.dto.*;
import kr.co.apiy.auth.entity.Member;
import kr.co.apiy.auth.exception.LoginFailException;
import kr.co.apiy.auth.exception.SocialLoginFailException;
import kr.co.apiy.auth.member.MemberRepository;
import kr.co.apiy.global.utils.ApiRequest;
import kr.co.apiy.global.utils.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Log4j2
public class SocialUserLoginApi {

    private final CustomUserDetailsService customUserDetailsService;
    private final MemberRepository memberRepository;
    private final ApiRequest apiRequest;
    private final JsonUtils jsonUtils;
    private final String GOOGLE_CLIENT_ID;
    private final String GOOGLE_CLIENT_SECRET;

    @Autowired
    public SocialUserLoginApi(
            CustomUserDetailsService customUserDetailsService,
            MemberRepository memberRepository,
            ApiRequest apiRequest,
            JsonUtils jsonUtils,
            @Value("${google.client.id}") String GOOGLE_CLIENT_ID,
            @Value("${google.client.secret}") String GOOGLE_CLIENT_SECRET
    ) {
        this.customUserDetailsService = customUserDetailsService;
        this.memberRepository = memberRepository;
        this.apiRequest = apiRequest;
        this.jsonUtils = jsonUtils;
        this.GOOGLE_CLIENT_ID = GOOGLE_CLIENT_ID;
        this.GOOGLE_CLIENT_SECRET = GOOGLE_CLIENT_SECRET;
    }


    public UsernamePasswordAuthenticationToken createGoogleToken(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        String socialCode = loginRequest.getSocialCode();
        GoogleUserInfoApiResult userInfo = checkGoogleUser(socialCode, passwordEncoder);
        return new UsernamePasswordAuthenticationToken(userInfo.getEmail(), userInfo.getId());
    }

    private GoogleUserInfoApiResult checkGoogleUser(String socialCode, PasswordEncoder passwordEncoder) {

        GoogleUserInfoApiResult userInfo = null;
        try {
            String accessToken = getToken(socialCode);
            userInfo = getUserInfo(accessToken);
            customUserDetailsService.loadUserByUsername(userInfo.getEmail());

            // 구글 로그인 이력이 없는 유저일 경우 loadUserByuUsername 에서 exception, 유저정보 저장.
        } catch (LoginFailException e) {
            try{
                Member newMember = Member.builder()
                        .email(userInfo.getEmail())
                        .name(userInfo.getName())
                        .password(passwordEncoder.encode(userInfo.getId()))
                        .build();
                newMember.addMemberRole(MemberRole.USER);
                memberRepository.save(newMember);
            }catch (Exception e2){
                log.warn(e2);
                throw new SocialLoginFailException("구글 유저 정보획득에 실패했습니다.");
            }

        } catch (Exception e) {
            log.warn(e);
            throw new SocialLoginFailException("구글 유저 정보획득에 실패했습니다.");
        }

        return userInfo;
    }

    private String getToken(String socialCode) {
        String baseUri = "https://oauth2.googleapis.com";
        String subUri = "/token";

        String requestFrom = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .toUriString();
        URI fromUri = URI.create(requestFrom);

        String redirectUri = fromUri.getScheme() + "://" + fromUri.getHost() + "/login/google";

        SocialLoginApiRequest socialLoginApiRequest = SocialLoginApiRequest.builder()
                .grant_type("authorization_code")
                .redirect_uri(redirectUri)
                .client_id(GOOGLE_CLIENT_ID)
                .client_secret(GOOGLE_CLIENT_SECRET)
                .code(socialCode)
                .build();

        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        String result = apiRequest.post(baseUri, subUri, socialLoginApiRequest, headers);
        SocialLoginApiResult response = jsonUtils.fromJson(result, SocialLoginApiResult.class);
        return response.getToken_type() + " " + response.getAccess_token();
    }

    private GoogleUserInfoApiResult getUserInfo(String accessToken) {
        String baseUri = "https://www.googleapis.com";
        String subUri = "/oauth2/v2/userinfo";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", accessToken);
        String result = apiRequest.get(baseUri, subUri, new HashMap<>(), headers);
        return jsonUtils.fromJson(result, GoogleUserInfoApiResult.class);
    }
}
