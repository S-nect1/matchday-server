package com.example.moim.user.service;

import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.jwt.JWTUtil;
import com.example.moim.user.dto.GoogleUserSignup;
import com.example.moim.user.dto.KakaoUserSignup;
import com.example.moim.user.dto.LoginOutput;
import com.example.moim.user.dto.NaverUserSignup;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;


@Service
@RequiredArgsConstructor
public class SocialLoginService {
    private final UserRepository userRepository;
    private final UserClubRepository userClubRepository;
    private final JWTUtil jwtUtil;
    private final RestTemplate restTemplate;

//    @Value("${spring.security.oauth2.client.registration.google.client-id}")
//    private String GOOGLE_CLIENT_ID;
//    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
//    private String GOOGLE_CLIENT_SECRET;
//    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
//    private String GOOGLE_REDIRECT_URL;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String KAKAO_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String KAKAO_REDIRECT_URL;
//    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
//    private String NAVER_CLIENT_ID;
//    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
//    private String NAVER_CLIENT_SECRET;

//    public LoginOutput googleLogin(String code) {
//        Map<String, String> params = new HashMap<>();
//        code = URLDecoder.decode(code, StandardCharsets.UTF_8);
//        params.put("code", code);
//        params.put("client_id", GOOGLE_CLIENT_ID);
//        params.put("client_secret", GOOGLE_CLIENT_SECRET);
//        params.put("redirect_uri", GOOGLE_REDIRECT_URL);
//        params.put("grant_type", "authorization_code");
//
//        ResponseEntity<JsonNode> tokenResponse = restTemplate.postForEntity("https://oauth2.googleapis.com/token", params, JsonNode.class);
//        String accessToken = tokenResponse.getBody().get("access_token").asText();
//
//        // "access_token" 필드의 값을 추출후 토큰으로 정보 얻어옴
//        GoogleUserSignup googleUserSignup = restTemplate
//                .getForEntity("https://people.googleapis.com/v1/people/me?personFields=emailAddresses,genders&access_token="
//                        + accessToken, GoogleUserSignup.class).getBody();
//
//        return getLoginOutput(User.createGoogleUser(googleUserSignup));
//    }

    public LoginOutput kakaoLogin(String accessToken) {
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("code", code);
//        params.add("client_id", KAKAO_CLIENT_ID);
//        params.add("redirect_uri", KAKAO_REDIRECT_URL);
//        params.add("grant_type", "authorization_code");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(CONTENT_TYPE, "application/x-www-form-urlencoded");
//
//        ResponseEntity<JsonNode> tokenResponse = restTemplate.postForEntity("https://kauth.kakao.com/oauth/token", new HttpEntity<>(params, httpHeaders), JsonNode.class);
//        String accessToken = tokenResponse.getBody().get("access_token").asText();

//        // "access_token" 필드의 값을 추출후 토큰으로 정보 얻어옴
        httpHeaders.set(AUTHORIZATION, "Bearer " + accessToken);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(httpHeaders);
        JsonNode body = restTemplate
                .exchange("https://kapi.kakao.com/v2/user/me?property_keys=[\"kakao_account.email\"]",
                        HttpMethod.GET, httpEntity, JsonNode.class).getBody().get("kakao_account");

        return getLoginOutput(User.createKakaoUser(new KakaoUserSignup(body.get("email").asText())));
    }

//    public LoginOutput naverLogin(String code) {
//        String accessToken = restTemplate.exchange("https://nid.naver.com/oauth2.0/token?code=" + code + "&client_id=" + NAVER_CLIENT_ID +
//                        "&client_secret=" + NAVER_CLIENT_SECRET + "&grant_type=authorization_code&state=%2F%2A-%2B"
//                , HttpMethod.GET, null, JsonNode.class).getBody().get("access_token").asText();
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set(AUTHORIZATION, "Bearer " + accessToken);
//        JsonNode body = restTemplate.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.GET, new HttpEntity<>(httpHeaders), JsonNode.class)
//                .getBody().get("response");
//
//        return getLoginOutput(User.createNaverUser(new NaverUserSignup(body.get("gender").asText(), body.get("email").asText())));
//    }

    private LoginOutput getLoginOutput(User user) {
        Optional<User> findUser = userRepository.findByEmail(user.getEmail());
        if (findUser.isEmpty()) {
            user.setRefreshToken(jwtUtil.createRefreshToken(user));
            user = userRepository.save(user);
            Boolean hasClub = userClubRepository.existsByUser(user);
            return new LoginOutput(user, jwtUtil.createAccessToken(user), hasClub);
        }
        user = findUser.get();
        Boolean hasClub = userClubRepository.existsByUser(user);
        return new LoginOutput(user, jwtUtil.createAccessToken(user), hasClub);
    }
}
