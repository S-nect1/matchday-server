package com.example.moim.user.service;

import com.example.moim.user.dto.JoinDTO;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public void joinProcess(JoinDTO joinDTO) {
        
        String email = joinDTO.getEmail();
        joinDTO.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        String password = joinDTO.getPassword();
        Boolean isExist = userRepository.existsByEmailAndPassword(email, password);
        if (isExist) {
            return;
        }
        userRepository.save(User.createUser(joinDTO));
    }
    
//    public LoginInput login(LoginInput loginInput) {
//        if (!userRepository.existsByEmailAndPassword(loginInput.getEmail(), loginInput.getPassword())) {
//
//        }
//    }
}
