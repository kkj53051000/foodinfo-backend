package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.CollabPlatform;
import com.kp.foodinfo.dto.CollabPlatformDto;
import com.kp.foodinfo.dto.FileTestUtilDto;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.CollabEventRepository;
import com.kp.foodinfo.repository.CollabPlatformRepository;
import com.kp.foodinfo.request.CollabEventRequest;
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
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollabEventServiceTest {
    @Autowired
    CollabEventService collabEventService;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CollabPlatformRepository collabPlatformRepository;

    @Autowired
    CollabEventRepository collabEventRepository;

    @Test
    @Transactional
    void COLLAB_EVENT_SAVE_TEST() {
        //given
        //파일 가져오기
        FileTestUtilDto fileTestUtilDto = FileTestUtil.getTestMultifile();

        //브랜드
        Brand brand = new Brand("pizzaHut", "test/test.jpg");
        brandRepository.save(brand);

        //콜라보 플랫폼
        CollabPlatform collabPlatform = new CollabPlatform("card", "test/test.jpg");
        collabPlatformRepository.save(collabPlatform);

        //리퀘스트 파라미터
        Date startDate = new Date();
        Date endDate = new Date();

        CollabEventRequest collabEventRequest = new CollabEventRequest("title", "content", startDate, endDate, brand.getId(), collabPlatform.getId());

        //when
        collabEventService.saveCollabEvent(fileTestUtilDto.getMultipartFile(), collabEventRequest, fileTestUtilDto.getRealPath());

        //then
        Assertions.assertNotNull(collabEventRepository.findByTitle(collabEventRequest.getTitle()).get());
    }
}