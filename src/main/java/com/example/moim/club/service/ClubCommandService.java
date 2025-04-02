package com.example.moim.club.service;

import com.example.moim.club.dto.request.*;
import com.example.moim.club.dto.response.*;
import com.example.moim.user.entity.User;

import java.io.IOException;
import java.util.List;

public interface ClubCommandService {
    ClubSaveOutput saveClub(User user, ClubInput clubInput) throws IOException;
    ClubUpdateOutput updateClub(User user, ClubUpdateInput clubUpdateInput, Long clubId) throws IOException;
    UserClubOutput saveClubUser(User user, ClubUserSaveInput clubUserSaveInput);
    UserClubOutput updateClubUser(User user, ClubUserUpdateInput clubInput);
    void clubPasswordUpdate(User user, ClubPswdUpdateInput clubPswdUpdateInput);

}
