package com.kp.foodinfo.service;

import com.kp.foodinfo.dto.CollabPlatformDto;
import com.kp.foodinfo.dto.FileTestUtilDto;
import com.kp.foodinfo.repository.CollabPlatformRepository;
import com.kp.foodinfo.util.FileTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollabPlatformServiceTest {

    @Autowired
    CollabPlatformService collabPlatformService;

    @Autowired
    CollabPlatformRepository collabPlatformRepository;

    @Test
    @Transactional
    void COLLAB_PLATFORM_SAVE_TEST() {
        //given
        //테스트 파일 가져오기
        FileTestUtilDto fileTestUtilDto = FileTestUtil.getTestMultifile();

        //collabPlatformDto 파라미터 생성
        CollabPlatformDto collabPlatformDto = new CollabPlatformDto("test", fileTestUtilDto.getMultipartFile(), fileTestUtilDto.getRealPath());

        //when
        collabPlatformService.saveCollabPlatform(collabPlatformDto);

        //then
        Assertions.assertNotNull(collabPlatformRepository.findByName(collabPlatformDto.getName()).get());
    }
}