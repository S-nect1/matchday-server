package com.example.moim.global.util.file.service;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.example.moim.global.exception.ResponseCode;
import com.example.moim.global.util.file.exception.advice.AwsS3ControllerAdvice;
import com.example.moim.global.util.file.model.FileInfo;
import com.example.moim.global.util.uuid.UuidHolder;
import com.example.moim.schedule.exception.advice.ScheduleControllerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
class CloudFileServiceTest {

    @Autowired
    private AwsS3FileService fileService;

    @Value("${cloud.aws.s3.bucket}")
    String bucket;

    @Test
    void upload() throws IOException {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("name", "originalName.jpg", "image/png", "ddd".getBytes());

        log.debug("originalName = {}", mockMultipartFile.getOriginalFilename());

        //when
        FileInfo fileInfo = fileService.upload(mockMultipartFile, "test");

        //then
        assertThat(fileInfo.getFileUrl()).contains("/test");
        assertThat(fileService.doesObjectExist("test/aaaa-aaaa-aaaa.jpg")).isTrue();

        fileService.remove(fileInfo.getStoredFileName());
    }

    @Test
    void upload_wrong_file_type() throws IOException {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("name", "originalName", "image/png", "ddd".getBytes());

        log.debug("originalName = {}", mockMultipartFile.getOriginalFilename());

        //when
        //then
        Exception exception = assertThrows(AwsS3ControllerAdvice.class, () -> {
            fileService.upload(mockMultipartFile, "test");
        });
        assertThat(exception.getMessage()).isEqualTo(ResponseCode.FILE_CONTENT_TYPE_NOT_IMAGE.getMessage());
    }

    @Test
    void remove() throws IOException {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("delete file", "delete_originalName.jpg", "image/png", "delete".getBytes());

        FileInfo fileInfo = fileService.upload(mockMultipartFile, "test");

        //when
        fileService.remove(fileInfo.getStoredFileName());

        //then
        assertThat(fileService.doesObjectExist(fileInfo.getStoredFileName())).isFalse();

    }
}