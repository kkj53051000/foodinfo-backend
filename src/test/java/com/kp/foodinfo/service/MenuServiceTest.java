package com.kp.foodinfo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandMenuKind;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.dto.FileTestUtilDto;
import com.kp.foodinfo.repository.*;
import com.kp.foodinfo.request.MenuRequest;
import com.kp.foodinfo.util.FileTestUtil;
import com.kp.foodinfo.vo.MenuListVo;
import com.kp.foodinfo.vo.MenuVoList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MenuServiceTest {
    @Autowired
    MenuService menuService;

    @Autowired
    BrandMenuKindRepository brandMenuKindRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    MenuSizeRepositroy menuSizeRepositroy;



    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Transactional
    void MENU_SAVE_TEST() throws IOException {
        //given
        FileTestUtilDto fileTestUtilDto = FileTestUtil.getTestMultifile();

        //Mock MenuService 생성
        FileService fileService = Mockito.mock(FileService.class);
        MenuService menuService = new MenuService(menuRepository, brandMenuKindRepository, fileService, brandRepository, menuSizeRepositroy);

        //Food save
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        //Brand 저장
        Brand brand = new Brand("pizzaHut", "test/test.jpg", new Date(), food);
        brandRepository.save(brand);

        //BrandMenuKind 저장
        BrandMenuKind brandMenuKind = new BrandMenuKind("메인 메뉴", 1, brand);
        brandMenuKindRepository.save(brandMenuKind);

        MenuRequest menuRequest = new MenuRequest("test", 10000, brandMenuKind.getId());

        //when
        menuService.saveMenu(fileTestUtilDto.getMultipartFile(), menuRequest);

        //then
        Assertions.assertNotNull(menuRepository.findByName(menuRequest.getName()).get());
    }

    @Test
    @Transactional
    void MENU_GET_LIST_TEST() throws JsonProcessingException {
        //given
        //Mock MenuService 생성
        FileService fileService = Mockito.mock(FileService.class);
        MenuService menuService = new MenuService(menuRepository, brandMenuKindRepository, fileService, brandRepository, menuSizeRepositroy);

        //Food save
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        //Brand 저장
        Brand brand = new Brand("pizzaHut", "test/test.jpg", new Date(), food);
        brandRepository.save(brand);

        //BrandMenuKind 저장
        BrandMenuKind brandMenuKind = new BrandMenuKind("메인 메뉴", 1, brand);
        brandMenuKindRepository.save(brandMenuKind);

        for (int i = 0; i < 10; i++) {
            Menu menu = new Menu("normalPizza" + i, 20000, "test/test.jpg", brandMenuKind);

            menuRepository.save(menu);
        }

        //when
        MenuVoList menuVoList = menuService.getMenuList(brandMenuKind.getId());

        String jsonMenuListVo = objectMapper.writeValueAsString(menuVoList);


        Assertions.assertEquals(jsonMenuListVo, objectMapper.writeValueAsString(menuService.getMenuList(brandMenuKind.getId())));
    }
}