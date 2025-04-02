package com.example.moim.club.dto.response;

import com.example.moim.club.entity.UserClub;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.FileUrlResource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;

@Data
@NoArgsConstructor
public class UserClubOutput {
    private Long userId;
    private Long clubId;
    private String name;
    private String clubRole;
    private LocalDate joinDate;
    private String birthday;
    private String img;

    public UserClubOutput(UserClub userClub) {
        this.userId = userClub.getUser().getId();
        this.clubId = userClub.getClub().getId();
        this.name = userClub.getUser().getName();
        this.clubRole = userClub.getClubRole().name();
        this.joinDate = userClub.getJoinDate();
        this.birthday = userClub.getUser().getBirthday();
        if (StringUtils.hasText(userClub.getUser().getImgPath())) {
            try {
                this.img = Base64.getEncoder().encodeToString(new FileUrlResource(userClub.getUser().getImgPath()).getContentAsByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
