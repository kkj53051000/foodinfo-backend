package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandMenuKind;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.domain.Menu;
import com.kp.foodinfo.dto.FileTestUtilDto;
import com.kp.foodinfo.repository.BrandMenuKindRepository;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.repository.MenuRepository;
import com.kp.foodinfo.request.MenuRequest;
import com.kp.foodinfo.util.FileTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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

    @Test
    @Transactional
    void MENU_SAVE_TEST() throws IOException {
        //given
        FileTestUtilDto fileTestUtilDto = FileTestUtil.getTestMultifile();

        //Mock MenuService 생성
        FileService fileService = Mockito.mock(FileService.class);
        MenuService menuService = new MenuService(menuRepository, brandMenuKindRepository, fileService);

        //Food save
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        //Brand 저장
        Brand brand = new Brand("pizzaHut", "test/test.jpg", food);
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
    void MENU_GET_LIST_TEST() {
        //given
        //Mock MenuService 생성
        FileService fileService = Mockito.mock(FileService.class);
        MenuService menuService = new MenuService(menuRepository, brandMenuKindRepository, fileService);

        //Food save
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        //Brand 저장
        Brand brand = new Brand("pizzaHut", "test/test.jpg", food);
        brandRepository.save(brand);

        //BrandMenuKind 저장
        BrandMenuKind brandMenuKind = new BrandMenuKind("메인 메뉴", 1, brand);
        brandMenuKindRepository.save(brandMenuKind);

        for (int i = 0; i < 10; i++) {
            Menu menu = new Menu("normalPizza" + i, 20000, "test/test.jpg", brandMenuKind);

            menuRepository.save(menu);
        }

        //when
        List<Menu> menus = menuService.getMenuList(brandMenuKind.getId());

        //then
        Assertions.assertEquals(menus.size(), 10);
    }
}