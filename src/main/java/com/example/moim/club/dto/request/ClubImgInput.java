package com.example.moim.club.dto.request;

import com.example.moim.global.util.file.exception.ValidFile;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ClubImgInput {
    private Long id;
    @ValidFile(message = "모임에 설정할 이미지를 넣어주세요.")
    private MultipartFile img;
}
