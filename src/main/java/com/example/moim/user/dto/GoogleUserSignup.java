package com.example.moim.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class GoogleUserSignup {
    private String resourceName;
    private String etag;
    private List<Map<String,Object>> genders;
    private List<Map<String,Object>> emailAddresses;

}
