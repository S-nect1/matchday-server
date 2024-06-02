package com.example.moim.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SocialSignupInput {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "생년월일을 입력해주세요.")
    private String birthday;
    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "010(\\d{4})(\\d{4})$", message = "휴대폰 번호 양식에 맞지 않습니다.")
    private String phone;
    private MultipartFile img;
    private String fcmToken;
}
