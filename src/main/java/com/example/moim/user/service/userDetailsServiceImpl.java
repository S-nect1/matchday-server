package com.example.moim.user.service;

import com.example.moim.user.entity.User;
import com.example.moim.user.repository.UserRepository;
import com.example.moim.user.dto.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class userDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email){
        //DB에서 조회
        User user = userRepository.findByEmail(email).orElse(null);
        
        if (user != null) {
            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new UserDetailsImpl(user);
        }
        throw new UsernameNotFoundException("loadUserByUsername");
    }
}
