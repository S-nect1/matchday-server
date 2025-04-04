package com.example.moim.global.util.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.global.util.file.model.AwsS3;
import com.example.moim.global.util.file.exception.advice.AwsS3ControllerAdvice;
import com.example.moim.global.util.uuid.UuidHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
//@Service
//@Profile("!test")
@RequiredArgsConstructor
public class AwsS3FileService implements FileService {

    private final AmazonS3 amazonS3;
    private final UuidHolder uuidHolder;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String directoryName) {

        // 확장자 가져오기
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileName = uuidHolder.randomUuid() + extension;
        String key = directoryName + "/" + fileName;
        log.info("upload key : {}", key);

        // 메타 데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try {
            // 파일 업로드
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead); // Public Read 권한 설정
            amazonS3.putObject(putObjectRequest);
        } catch (IOException e) {
            throw new AwsS3ControllerAdvice(ResponseCode.S3_UPLOAD_FAIL);
        }

        String path = amazonS3.getUrl(bucket, key).toString();
        log.info("upload path : {}", path);

        AwsS3 result = AwsS3
                .builder()
                .key(key)
                .path(path)
                .build();

        return result.getPath();
    }

    public void remove(String path) {

        if (!amazonS3.doesObjectExist(bucket, path)) {
            log.error("{} 이 존재하지 않습니다.", path);
            throw new AwsS3ControllerAdvice(ResponseCode.S3_PATH_NOT_FOUND);
        }
        amazonS3.deleteObject(bucket, path);

    }

}
