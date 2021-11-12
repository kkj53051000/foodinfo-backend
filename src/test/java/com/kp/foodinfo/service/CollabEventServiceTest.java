package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.CollabEvent;
import com.kp.foodinfo.domain.CollabPlatform;
import com.kp.foodinfo.dto.CollabPlatformDto;
import com.kp.foodinfo.dto.FileTestUtilDto;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.CollabEventRepository;
import com.kp.foodinfo.repository.CollabPlatformRepository;
import com.kp.foodinfo.request.CollabEventListRequest;
import com.kp.foodinfo.request.CollabEventRequest;
import com.kp.foodinfo.util.FileTestUtil;
import com.kp.foodinfo.vo.CollabEventInfoVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Mock
    FileService fileService;

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
        CollabEventRequest collabEventRequest = new CollabEventRequest("title", "content", "2021-01-01", "00:00",  "2021-01-01", "00:00", brand.getId(), collabPlatform.getId());

        //when
        collabEventService.saveCollabEvent(fileTestUtilDto.getMultipartFile(), collabEventRequest, fileTestUtilDto.getRealPath());

        //then
        Assertions.assertNotNull(collabEventRepository.findByTitle(collabEventRequest.getTitle()).get());
    }

    @Test
    @Transactional
    void COLLAB_EVENT_GET_INFO_TEST() {
        //given
        //파일 가져오기
        FileTestUtilDto fileTestUtilDto = FileTestUtil.getTestMultifile();

        //브랜드
        Brand brand = new Brand("pizzaHut", "test/test.jpg");
        brandRepository.save(brand);

        //콜라보 플랫폼
        CollabPlatform collabPlatformCard = new CollabPlatform("card", "test/test.jpg");
        collabPlatformRepository.save(collabPlatformCard);

        CollabPlatform collabPlatformPoint = new CollabPlatform("point", "test/test.jpg");
        collabPlatformRepository.save(collabPlatformPoint);

        //Mock CollabEventService
        Mockito.when(fileService.imageUploadProcess(fileTestUtilDto.getMultipartFile(), fileTestUtilDto.getRealPath())).thenReturn("test/test.jpg");
        CollabEventService collabEventService = new CollabEventService(collabEventRepository, fileService, brandRepository, collabPlatformRepository);

        //리퀘스트 파라미터
        for(int i = 0; i < 10; i++) {
            if(i % 2 == 0) {
                CollabEventRequest collabEventRequest = new CollabEventRequest("title", "content", "2021-01-01", "00:00","2021-01-01", "00:00", brand.getId(), collabPlatformCard.getId());
                collabEventService.saveCollabEvent(fileTestUtilDto.getMultipartFile(), collabEventRequest, fileTestUtilDto.getRealPath());
            }else{
                CollabEventRequest collabEventRequest = new CollabEventRequest("title", "content",  "2021-01-01", "00:00","2021-01-01", "00:00", brand.getId(), collabPlatformPoint.getId());
                collabEventService.saveCollabEvent(fileTestUtilDto.getMultipartFile(), collabEventRequest, fileTestUtilDto.getRealPath());
            }
        }

        //비교 대상
        List<CollabEventInfoVo> collabEventInfoVos1 = new ArrayList<>();

        CollabEventInfoVo collabEventInfoVo1 = new CollabEventInfoVo("card", "test/test.jpg", 5);
        collabEventInfoVos1.add(collabEventInfoVo1);

        CollabEventInfoVo collabEventInfoVo2 = new CollabEventInfoVo("point", "test/test.jpg", 5);
        collabEventInfoVos1.add(collabEventInfoVo2);

        for (CollabEventInfoVo c : collabEventInfoVos1){
            System.out.println("1 : name : " + c.getName() + " img : " + c.getImg() + " count : " + c.getCount());
        }

        //when
        List<CollabEventInfoVo> collabEventInfoVos2 = collabEventService.getCollabEventInfo(brand.getId());

        for (CollabEventInfoVo c : collabEventInfoVos2){
            System.out.println("2 : name : " + c.getName() + " img : " + c.getImg() + " count : " + c.getCount());
        }

        //then
        for(int i = 0; i < collabEventInfoVos2.size(); i++){
            Assertions.assertEquals(collabEventInfoVos1.get(i).getName(), collabEventInfoVos2.get(i).getName());
            Assertions.assertEquals(collabEventInfoVos1.get(i).getImg(), collabEventInfoVos2.get(i).getImg());
            Assertions.assertEquals(collabEventInfoVos1.get(i).getCount(), collabEventInfoVos2.get(i).getCount());
        }
    }

    @Test
    @Transactional
    void COLLAB_EVENT_GET_LIST_TEST() {
        //given
        //파일 가져오기
        FileTestUtilDto fileTestUtilDto = FileTestUtil.getTestMultifile();

        //브랜드
        Brand brand = new Brand("pizzaHut", "test/test.jpg");
        brandRepository.save(brand);

        //콜라보 플랫폼
        CollabPlatform collabPlatformCard = new CollabPlatform("card", "test/test.jpg");
        collabPlatformRepository.save(collabPlatformCard);

        CollabPlatform collabPlatformPoint = new CollabPlatform("point", "test/test.jpg");
        collabPlatformRepository.save(collabPlatformPoint);

        //Mock CollabEventService
        Mockito.when(fileService.imageUploadProcess(fileTestUtilDto.getMultipartFile(), fileTestUtilDto.getRealPath())).thenReturn("test/test.jpg");
        CollabEventService collabEventService = new CollabEventService(collabEventRepository, fileService, brandRepository, collabPlatformRepository);

        //saveCollabEvent 리퀘스트 파라미터
        for(int i = 0; i < 10; i++) {
            if(i % 2 == 0) {
                CollabEventRequest collabEventRequest = new CollabEventRequest("title", "content", "2021-01-01", "00:00", "2021-01-01", "00:00", brand.getId(), collabPlatformCard.getId());
                collabEventService.saveCollabEvent(fileTestUtilDto.getMultipartFile(), collabEventRequest, fileTestUtilDto.getRealPath());
            }else{
                CollabEventRequest collabEventRequest = new CollabEventRequest("title", "content", "2021-01-01", "00:00", "2021-01-01", "00:00", brand.getId(), collabPlatformPoint.getId());
                collabEventService.saveCollabEvent(fileTestUtilDto.getMultipartFile(), collabEventRequest, fileTestUtilDto.getRealPath());
            }
        }

        //getCollabEventList 리퀘스트 파라미터 (card CollabPlatform)
        CollabEventListRequest collabEventListRequest = new CollabEventListRequest(brand.getId(), collabPlatformCard.getId());

        //when
        List<CollabEvent> collabEvents = collabEventService.getCollabEventList(collabEventListRequest);

        //then
        Assertions.assertEquals(collabEvents.size(), 5);
    }
}