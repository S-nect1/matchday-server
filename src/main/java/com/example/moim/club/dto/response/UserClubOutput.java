package com.example.moim.club.dto.response;

import com.example.moim.club.entity.UserClub;
import lombok.Data;
import org.springframework.core.io.FileUrlResource;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;

@Data
public class UserClubOutput {
    private Long userId;
    private String name;
    private String category;
    private String position;
    private LocalDate joinDate;
    private String birthday;
    private String phone;
    private String img;

    public UserClubOutput(UserClub userClub) {
        this.userId = userClub.getId();
        this.name = userClub.getUser().getName();
        this.category = userClub.getCategory();
        this.position = userClub.getPosition();
        this.joinDate = userClub.getJoinDate();
        this.birthday = userClub.getUser().getBirthday();
        this.phone = userClub.getUser().getPhone();
        if (userClub.getUser().getImgPath() != null) {
            try {
                this.img = Base64.getEncoder().encodeToString(new FileUrlResource(userClub.getUser().getImgPath()).getContentAsByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
