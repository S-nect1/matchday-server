package com.example.moim.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserUpdateInput {
    private String name;
    @Schema(description = "2000/05/05", example = "2000/05/05")
    private String birthday;
    @Pattern(regexp = "010(\\d{4})(\\d{4})$", message = "휴대폰 번호 양식에 맞지 않습니다.")
    @Schema(description = "01012341234", example = "01012341234")
    private String phone;
    @Schema(description = "남성 = MAN / 여성 = WOMAN", example = "MAN")
    private String gender;
    private MultipartFile img;
    @Schema(description = "서울, 인천, 경기 북부, 경기 남부", example = "서울")
    private String activityArea;
    private Integer height;
    private Integer weight;
    @Schema(description = "주로 쓰는 발. 왼발, 오른발", example = "왼발")
    private String mainFoot;
    private String mainPosition;
    private String subPosition;
    private String fcmToken;
}
