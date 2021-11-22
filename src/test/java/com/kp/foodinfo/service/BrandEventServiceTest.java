package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandEvent;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.dto.FileTestUtilDto;
import com.kp.foodinfo.repository.BrandEventRepository;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.request.BrandEventRequest;
import com.kp.foodinfo.util.FileTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
class BrandEventServiceTest {

    @Autowired
    BrandEventService brandEventService;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    BrandEventRepository brandEventRepository;

    @Autowired
    FoodRepository foodRepository;

    @Test
    @Transactional
    void BRAND_EVENT_SAVE_TEST() throws IOException {
        //given
        //파일 가져오기
        FileTestUtilDto fileTestUtilDto = FileTestUtil.getTestMultifile();

        //Brand Save (Parameter)
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", food);
        brandRepository.save(brand);

        BrandEventRequest brandEventRequest = new BrandEventRequest("title", "content", "2021-01-01", "00:00", "2021-01-02", "00:00", brand.getId());

        //Mock
        FileService fileService = Mockito.mock(FileService.class);
        BrandEventService brandEventService = new BrandEventService(brandEventRepository, brandRepository, fileService);

        //when
        brandEventService.saveBrandEvent(fileTestUtilDto.getMultipartFile(), brandEventRequest);

        //then
        Assertions.assertNotNull(brandEventRepository.findByTitle(brandEventRequest.getTitle()).get());
    }

    @Test
    @Transactional
    void BRAND_EVENT_GET_BRAND_EVENTS() {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", food);
        brandRepository.save(brand);

        Date startDate = new Date();
        Date endDate = new Date();

        List<BrandEvent> brandEvents1 = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            BrandEvent brandEvent = new BrandEvent("title" + i, "content", "test/test.jpg", startDate, endDate, brand);

            brandEvents1.add(brandEvent);

            brandEventRepository.save(brandEvent);
        }

        //when
        List<BrandEvent> brandEvents2 = brandEventService.getBrandEvents(brand.getId());

        //then 저장 후 가져와서 길이로 비교
        Assertions.assertEquals(brandEvents1.size(), brandEvents2.size());
    }
}