package com.example.moim.user.service;

import com.example.moim.jwt.JWTUtil;
import com.example.moim.user.dto.GoogleUserSignup;
import com.example.moim.user.dto.LoginOutput;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class GoogleUserService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String LOGIN_REDIRECT_URL;

    public LoginOutput googleLogin(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("client_secret", GOOGLE_CLIENT_SECRET);
        params.put("redirect_uri", LOGIN_REDIRECT_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<JsonNode> tokenResponse = new RestTemplate().postForEntity("https://oauth2.googleapis.com/token", params, JsonNode.class);
        String accessToken = tokenResponse.getBody().get("access_token").asText();

        // "access_token" 필드의 값을 추출후 토큰으로 정보 얻어옴
        GoogleUserSignup googleUserSignup = new RestTemplate()
                .getForEntity("https://people.googleapis.com/v1/people/me?personFields=phoneNumbers,names,emailAddresses,birthdays,genders&access_token="
                        + accessToken, GoogleUserSignup.class).getBody();
        User googleUser = User.createGoogleUser(googleUserSignup);
        if (!userRepository.existsByEmail(googleUser.getEmail())) {
            googleUser = userRepository.save(googleUser);
        } else {
            googleUser = userRepository.findByEmail(googleUser.getEmail()).get();
        }
        return new LoginOutput(googleUser, jwtUtil.createAccessToken(googleUser));
    }
}
