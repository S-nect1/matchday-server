package com.example.moim.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserUpdateInput {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "생년월일을 입력해주세요.")
    @Schema(description = "2000/05/05", example = "2000/05/05")
    private String birthday;
    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "010(\\d{4})(\\d{4})$", message = "휴대폰 번호 양식에 맞지 않습니다.")
    @Schema(description = "01012341234", example = "01012341234")
    private String phone;
    @NotBlank
    @Schema(description = "남성 = MAN / 여성 = WOMAN", example = "MAN")
    private String gender;
    private MultipartFile img;
    @NotBlank
    @Schema(description = "서울, 인천, 경기 북부, 경기 남부", example = "서울")
    private String activityArea;
    private String introduction;
    private String fcmToken;
}
