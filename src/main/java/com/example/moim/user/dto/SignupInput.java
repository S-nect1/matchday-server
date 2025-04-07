package com.example.moim.user.dto;

import com.example.moim.global.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
public class SignupInput {
    @Email(message = "올바른 형식의 이메일 주소여야 합니다.")
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String birthday;

    private String gender;
    @NotBlank
    @Pattern(regexp = "010(\\d{4})(\\d{4})$", message = "휴대폰 번호 양식에 맞지 않습니다.")
    private String phone;

    @Builder
    public SignupInput(String email, String password, String name, String birthday, String gender, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
    }
}