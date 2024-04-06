package com.example.moim.user.repository;

import com.example.moim.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmailAndPassword(String email, String password);
    
    Optional<User> findByEmail(String email);

}
