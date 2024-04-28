package com.example.moim.user.dto;

import com.example.moim.user.entity.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupInput {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String birthday;
    @NotBlank
    private Gender gender;
    @NotBlank
    private String phone;
}