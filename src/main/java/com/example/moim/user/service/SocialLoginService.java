package com.example.moim.user.service;

import com.example.moim.jwt.JWTUtil;
import com.example.moim.user.dto.GoogleUserSignup;
import com.example.moim.user.dto.KakaoUserSignup;
import com.example.moim.user.dto.LoginOutput;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;


@Service
@RequiredArgsConstructor
public class SocialLoginService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URL;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String KAKAO_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String KAKAO_REDIRECT_URL;

    public LoginOutput googleLogin(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("client_secret", GOOGLE_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_REDIRECT_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<JsonNode> tokenResponse = new RestTemplate().postForEntity("https://oauth2.googleapis.com/token", params, JsonNode.class);
        String accessToken = tokenResponse.getBody().get("access_token").asText();

        // "access_token" 필드의 값을 추출후 토큰으로 정보 얻어옴
        GoogleUserSignup googleUserSignup = new RestTemplate()
                .getForEntity("https://people.googleapis.com/v1/people/me?personFields=emailAddresses,genders&access_token="
                        + accessToken, GoogleUserSignup.class).getBody();
        User googleUser = User.createGoogleUser(googleUserSignup);
        Optional<User> findUser = userRepository.findByEmail(googleUser.getEmail());
        if (findUser.isEmpty()) {
            googleUser = userRepository.save(googleUser);
            return new LoginOutput(googleUser, jwtUtil.createAccessToken(googleUser));
        }
        googleUser = findUser.get();
        return new LoginOutput(googleUser, jwtUtil.createAccessToken(googleUser));
    }

    public LoginOutput kakoLogin(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", KAKAO_CLIENT_ID);
        params.add("redirect_uri", KAKAO_REDIRECT_URL);
        params.add("grant_type", "authorization_code");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(CONTENT_TYPE, "application/x-www-form-urlencoded");

        ResponseEntity<JsonNode> tokenResponse = new RestTemplate().postForEntity("https://kauth.kakao.com/oauth/token", new HttpEntity<>(params, httpHeaders), JsonNode.class);
        String accessToken = tokenResponse.getBody().get("access_token").asText();

//        // "access_token" 필드의 값을 추출후 토큰으로 정보 얻어옴
        httpHeaders = new HttpHeaders();
        httpHeaders.set(AUTHORIZATION, "Bearer " + accessToken);
        httpHeaders.set(CONTENT_TYPE, "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(httpHeaders);
        JsonNode body = new RestTemplate()
                .exchange("https://kapi.kakao.com/v2/user/me?property_keys=[\"kakao_account.gender\",\"kakao_account.email\"]",
                        HttpMethod.GET, httpEntity, JsonNode.class).getBody().get("kakao_account");

        User kakaoUser = User.createKakaoUser(new KakaoUserSignup(body.get("gender").asText(), body.get("email").asText()));
        Optional<User> findUser = userRepository.findByEmail(kakaoUser.getEmail());
        if (findUser.isEmpty()) {
            kakaoUser = userRepository.save(kakaoUser);
            return new LoginOutput(kakaoUser, jwtUtil.createAccessToken(kakaoUser));
        }
        kakaoUser = findUser.get();
        return new LoginOutput(kakaoUser, jwtUtil.createAccessToken(kakaoUser));
    }
}
