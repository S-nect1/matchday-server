package com.example.moim.user.service;

import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.jwt.JWTUtil;
import com.example.moim.user.dto.*;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserClubRepository userClubRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    
    public void signup(SignupInput signupInput) {
        signupInput.setPassword(bCryptPasswordEncoder.encode(signupInput.getPassword()));
        if (userRepository.existsByEmail(signupInput.getEmail())) {
            throw new EntityExistsException("이미 가입된 계정입니다");
        }
        userRepository.save(User.createUser(signupInput));
    }

    @Transactional
    public LoginOutput login(LoginInput loginInput) {
        //스프링 시큐리티에서 email password를 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginInput.getEmail(), loginInput.getPassword(), null);
        //token에 담은 검증을 위한 AuthenticationManager로 전달
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationManager.authenticate(authToken).getPrincipal();
        if (loginInput.getFcmToken() != null) {
            userDetails.getUser().setFcmToken(loginInput.getFcmToken());
        }
        userDetails.getUser().setRefreshToken(jwtUtil.createRefreshToken(userDetails.getUser()));
        return new LoginOutput(userDetails.getUser(), jwtUtil.createAccessToken(userDetails.getUser()));
    }

    public UserOutput findUser(User user) {
        return new UserOutput(userRepository.findById(user.getId()).get());
    }

    public List<MyClubOutput> findUserClub(User user) {
        return userClubRepository.findByUser(user).stream().map(MyClubOutput::new).toList();
    }

    @Transactional
    public void saveUserInfo(User loginUser, SocialSignupInput socialSignupInput) {
        User user = userRepository.findById(loginUser.getId()).get();
        user.fillUserInfo(socialSignupInput);
    }

    @Transactional
    public LoginOutput userRefresh(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken).get();
        user.setRefreshToken(jwtUtil.createRefreshToken(user));
        return new LoginOutput(user, jwtUtil.createAccessToken(user));
    }
}
