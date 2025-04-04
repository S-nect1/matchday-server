package com.example.moim.global.util.file.model;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AwsS3 {

    private String key;
    private String path;

}
