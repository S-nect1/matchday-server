package com.example.moim.user.service;

import com.example.moim.club.repository.UserClubRepository;
import com.example.moim.user.dto.MyClubOutput;
import com.example.moim.user.dto.UserOutput;
import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import com.example.moim.user.dto.JoinInput;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserClubRepository userClubRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public void joinProcess(JoinInput joinInput) {
        joinInput.setPassword(bCryptPasswordEncoder.encode(joinInput.getPassword()));
        if (userRepository.existsByEmailAndPassword(joinInput.getEmail(), joinInput.getPassword())) {
            throw new EntityExistsException("이미 가입된 계정입니다");
        }
        userRepository.save(User.createUser(joinInput));
    }

    public UserOutput findUser(User user) {
        return new UserOutput(userRepository.findById(user.getId()).get());
    }

    public List<MyClubOutput> findUserClub(User user) {
        return userClubRepository.findByUser(user).stream().map(MyClubOutput::new).toList();
    }

}
