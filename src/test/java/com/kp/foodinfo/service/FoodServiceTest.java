package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.dto.FileTestUtilDto;
import com.kp.foodinfo.dto.FoodDto;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.util.FileTestUtil;
import com.kp.foodinfo.vo.FoodVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FoodServiceTest {
    @Autowired
    FoodRepository foodRepository;


    @Autowired
    FoodService foodService;

    @Test
    @Transactional
    void FOOD_SAVE_TEST() throws IOException {

        //given
        //테스트 파일 가져오기
        FileTestUtilDto fileTestUtilDto = FileTestUtil.getTestMultifile();

        //Mock객체 생성
        FileService fileService = Mockito.mock(FileService.class);

        FoodService foodService = new FoodService(foodRepository, fileService);

        //foodDto 파라미터 생성
        FoodDto foodDto = new FoodDto("pizza", fileTestUtilDto.getMultipartFile());

        //when
        foodService.saveFood(foodDto);

        //then
        Assertions.assertNotNull(foodRepository.findByName(foodDto.getName()));
    }

    @Test
    @Transactional
    void FOOD_GET_LIST_TEST() throws IOException {
        //given
        //테스트 파일 가져오가
        FileTestUtilDto fileTestUtilDto = FileTestUtil.getTestMultifile();

        //Mock객체 생성
        FileService fileService = Mockito.mock(FileService.class);

        FoodService foodService = new FoodService(foodRepository, fileService);

        List<FoodDto> foodDtos = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            FoodDto foodDto = new FoodDto("pizza" + i, fileTestUtilDto.getMultipartFile());

            foodDtos.add(foodDto);

            //파일 저장 (service)
            foodService.saveFood(foodDto);
        }

        //when
        List<Food> foods = foodService.getFoodList();

        //then
        Assertions.assertEquals(foodDtos.size(), foods.size());
    }

}